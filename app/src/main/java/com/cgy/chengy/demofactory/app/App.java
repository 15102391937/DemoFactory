package com.cgy.chengy.demofactory.app;

import android.app.Application;

import com.cgy.chengy.demofactory.websocket.WsManager;
import com.orhanobut.logger.Logger;
import com.outim.yxopen.api.YXAPI;

/**
 * Created by ChenGY on 2017/7/25.
 */

public class App extends Application {
    private static App mInstance;
    private YXAPI yxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        yxapi = YXAPI.createYXAPI(this, "111603115735320");
    }

    public static App getInstance() {
        return mInstance;
    }

    public YXAPI getYxapi() {
        return yxapi;
    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                Logger.t("WsManager").d("应用回到前台调用重连方法");
                WsManager.getInstance().reconnect();
            }

            @Override
            public void onBecameBackground() {
                Logger.t("WsManager").d("应用回到后台调用？？方法");
            }
        });
    }
}
