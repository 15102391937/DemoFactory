package com.cgy.chengy.demofactory.websocket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;

import com.cgy.chengy.demofactory.BuildConfig;
import com.cgy.chengy.demofactory.app.App;
import com.cgy.chengy.demofactory.websocket.notify.NotifyListenerManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ChenGY on 2018-12-24.
 */
public class WsManager {
    //region singleton

    private static volatile WsManager mInstance;

    private WsManager() {
    }

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    //endregion

    //region WsStatus and listenerAdapter

    /**
     * WebSocket的状态
     */
    public enum WsStatus {
        CONNECT_SUCCESS,//连接成功
        CONNECT_FAIL,//连接失败
        CONNECTING,//正在连接
        AUTH_SUCCESS//授权成功
    }

    private WsStatus mStatus;

    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    private WsStatus getStatus() {
        return mStatus;
    }

    private WsListener mListener;

    /**
     * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
     * onTextMessage 收到文字信息
     * onConnected 连接成功
     * onConnectError 连接失败
     * onDisconnected 连接关闭
     */
    private class WsListener extends WebSocketAdapter {
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            Logger.t(TAG).d("receiverMsg:%s", text);
            Response response = Codec.decoder(text);//解析出第一层bean
            if (response.getRespEvent() == 10) {//响应
                //找到对应callback，并移除
                CallbackWrapper wrapper = callbacks.remove(Long.parseLong(response.getSeqId()));
                if (wrapper == null) {
                    Logger.t(TAG).d("(action:%s) not found callback", response.getAction());
                    return;
                }

                try {
                    //取消超时任务
                    wrapper.getTimeoutTask().cancel(true);
                    ChildResponse childResponse = Codec.decoderChildResp(response.getResp());//解析第二层bean
                    if (childResponse.isOK()) {
                        Object o = new Gson().fromJson(childResponse.getData(), wrapper.getAction().getRespClazz());
                        wrapper.getTempCallback().onSuccess(o);
                    } else {
                        wrapper.getTempCallback()
                                .onError(ErrorCode.BUSINESS_EXCEPTION.getMsg(), wrapper.getRequest(), wrapper.getAction());
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    wrapper.getTempCallback()
                            .onError(ErrorCode.PARSE_EXCEPTION.getMsg(), wrapper.getRequest(), wrapper.getAction());
                }

            } else if (response.getRespEvent() == 20) {//通知
                NotifyListenerManager.getInstance().fire(response);
            }
        }

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            super.onConnected(websocket, headers);
            Logger.t(TAG).d("连接成功");
            setStatus(WsStatus.CONNECT_SUCCESS);
            cancelReconnect();//连接成功的时候取消重连,初始化连接次数
            doAuth();
        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
            super.onConnectError(websocket, exception);
            Logger.t(TAG).d("连接错误");
            setStatus(WsStatus.CONNECT_FAIL);
            reconnect();//连接错误的时候调用重连方法
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            Logger.t(TAG).d("断开连接");
            setStatus(WsStatus.CONNECT_FAIL);
            reconnect();//连接断开的时候调用重连方法
        }
    }

    //endregion

    //region method

    public void init() {
        try {
            /*
             * configUrl其实是缓存在本地的连接地址
             * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
             * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
             */
            String configUrl = "";
            url = TextUtils.isEmpty(configUrl) ? DEF_URL : configUrl;
            initWs();
            setStatus(WsStatus.CONNECTING);
            Logger.t(TAG).d("第一次连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (ws != null) ws.disconnect();
    }

    /**
     * 初始化WebSocket
     */
    private void initWs() throws IOException {
        ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
                .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                .setMissingCloseFrameAllowed(false)//不允许服务端关闭连接却未发送关闭帧
                .addListener(mListener = new WsListener())//添加回调监听
                .connectAsynchronously();//异步连接
    }

    public void reconnect() {
        if (!isNetConnect()) {
            reconnectCount = 0;
            Logger.t(TAG).d("重连失败网络不可用");
            return;
        }

        /*
         *这里其实应该还有个用户是否登录了的判断 因为当连接成功后我们需要发送用户信息到服务端进行校验
         *由于我们这里是个demo所以省略了
         *webSocket不为空 && 当前连接断开了 && 不是正在重连状态
         */
        if (ws != null && !ws.isOpen() && getStatus() != WsStatus.CONNECTING) {
            reconnectCount++;
            setStatus(WsStatus.CONNECTING);
            cancelHeartbeat();//取消心跳

            long reconnectTime = minInterval;
            if (reconnectCount > 3) {
                url = DEF_URL;
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }

            Logger.t(TAG).d("准备开始第%d次重连,重连间隔%d -- url:%s", reconnectCount, reconnectTime, url);
            mHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }

    private void cancelReconnect() {
        reconnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }

    /**
     * 判断当前网络是否可用
     */
    private boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 超时处理
     */
    private void timeoutHandle(Request request, Action action, ICallback callback, long timeout) {
        if (request.getReqCount() > 3) {
            Logger.t(TAG).d("(action:%s)连续3次请求超时 执行http请求", action.getAction());
            //走http请求
        } else {
            sendReq(action, request.getReq(), callback, timeout, request.getReqCount() + 1);
            Logger.t(TAG).d("(action:%s)发起第%d次请求", action.getAction(), request.getReqCount());
        }
    }

    public void sendReq(Action action, Object req, ICallback callback) {
        sendReq(action, req, callback, REQUEST_TIMEOUT);
    }

    public void sendReq(Action action, Object req, ICallback callback, long timeout) {
        sendReq(action, req, callback, timeout, 1);
    }

    /**
     * @param action   Action
     * @param req      请求参数
     * @param callback 回调
     * @param timeout  超时时间
     * @param reqCount 请求次数
     */
    @SuppressWarnings("unchecked")
    private <T> void sendReq(Action action, T req, final ICallback callback, final long timeout, int reqCount) {
        if (!isNetConnect()) {
            callback.onFail("网络不可用");
            return;
        }

        Request request = new Request.Builder<T>()
                .action(action.getAction())
                .reqEvent(action.getReqEvent())
                .seqId(seqId.getAndIncrement())
                .reqCount(reqCount)
                .req(req)
                .build();

        ScheduledFuture timeoutTask = enqueueTimeout(request.getSeqId(), timeout);//添加超时任务

        IWsCallback tempCallback = new IWsCallback() {

            @Override
            public void onSuccess(Object o) {
                mCallbackHandler.obtainMessage(SUCCESS_HANDLE, new CallbackDataWrapper(callback, o)).sendToTarget();
            }

            @Override
            public void onError(String msg, Request request, Action action) {
                mCallbackHandler.obtainMessage(ERROR_HANDLE, new CallbackDataWrapper(callback, msg)).sendToTarget();
            }

            @Override
            public void onTimeout(Request request, Action action) {
                timeoutHandle(request, action, callback, timeout);
            }
        };

        callbacks.put(request.getSeqId(), new CallbackWrapper(tempCallback, timeoutTask, action, request));
        Logger.t(TAG).d("send text : %s", new Gson().toJson(request));
        ws.sendText(new Gson().toJson(request));
    }

    /**
     * 添加超时任务
     */
    private ScheduledFuture enqueueTimeout(final long seqId, long timeout) {
        return executor.schedule(new Runnable() {
            @Override
            public void run() {
                CallbackWrapper wrapper = callbacks.remove(seqId);
                if (wrapper != null) {
                    Logger.t(TAG).d("(action:%s)第%d次请求超时", wrapper.getAction().getAction(), wrapper.getRequest().getReqCount());
                    wrapper.getTempCallback().onTimeout(wrapper.getRequest(), wrapper.getAction());
                }
            }
        }, timeout, TimeUnit.MILLISECONDS);
    }

    //endregion

    //region declare variable

    private final String TAG = this.getClass().getSimpleName();
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_TEST_URL = "测试服地址";//测试服默认地址
    private static final String DEF_RELEASE_URL = "正式服地址";//正式服默认地址
    private static final String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;
    private String url;
    private WebSocket ws;

    private static final int REQUEST_TIMEOUT = 10000;//请求超时时间
    private AtomicLong seqId = new AtomicLong(SystemClock.uptimeMillis());//每个请求的唯一标识
    private final int SUCCESS_HANDLE = 0x01;
    private final int ERROR_HANDLE = 0x02;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Map<Long, CallbackWrapper> callbacks = new HashMap<>();

    private int reconnectCount = 0;//重连次数
    private int heartbeatFailCount = 0;//心跳失败次数
    private static final long HEARTBEAT_INTERVAL = 30000;//心跳间隔
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔
    private Handler mHandler = new Handler();
    private Runnable mReconnectTask = () -> {
        try {
            initWs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    //请求回调接收的handler
    private Handler mCallbackHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_HANDLE:
                    CallbackDataWrapper successWrapper = (CallbackDataWrapper) msg.obj;
                    successWrapper.getCallback().onSuccess(successWrapper.getData());
                    break;
                case ERROR_HANDLE:
                    CallbackDataWrapper errorWrapper = (CallbackDataWrapper) msg.obj;
                    errorWrapper.getCallback().onFail((String) errorWrapper.getData());
                    break;
            }
        }
    };

    private Runnable heartbeatTask = new Runnable() {
        @Override
        public void run() {
            sendReq(Action.HEARTBEAT, null, new ICallback() {
                @Override
                public void onSuccess(Object o) {
                    heartbeatFailCount = 0;
                }


                @Override
                public void onFail(String msg) {
                    heartbeatFailCount++;
                    if (heartbeatFailCount >= 3) {
                        reconnect();
                    }
                }
            });

            mHandler.postDelayed(this, HEARTBEAT_INTERVAL);
        }
    };

    private void doAuth() {
        sendReq(Action.LOGIN, null, new ICallback() {
            @Override
            public void onSuccess(Object o) {
                Logger.t(TAG).d("授权成功");
                setStatus(WsStatus.AUTH_SUCCESS);
                //开始心跳
                startHeartbeat();
                //处理同步
                delaySyncData();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    //同步数据
    private void delaySyncData() {
        mHandler.postDelayed(() -> sendReq(Action.SYNC, null, new ICallback() {
            @Override
            public void onSuccess(Object o) {

            }


            @Override
            public void onFail(String msg) {

            }
        }), 300);
    }

    private void startHeartbeat() {
        mHandler.postDelayed(heartbeatTask, HEARTBEAT_INTERVAL);
    }

    private void cancelHeartbeat() {
        heartbeatFailCount = 0;
        mHandler.removeCallbacks(heartbeatTask);
    }

    //endregion
}


