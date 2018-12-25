package com.cgy.chengy.demofactory.websocket.notify;

import com.cgy.chengy.demofactory.websocket.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class NotifyListenerManager {
    //region singleton

    private volatile static NotifyListenerManager manager;

    private NotifyListenerManager() {
        regist();
    }

    public static NotifyListenerManager getInstance() {
        if (manager == null) {
            synchronized (NotifyListenerManager.class) {
                if (manager == null) {
                    manager = new NotifyListenerManager();
                }
            }
        }
        return manager;
    }

    //endregion

    private final String TAG = this.getClass().getSimpleName();
    private Map<String, INotifyListener> map = new HashMap<>();

    private void regist() {
        map.put("notifyAnnounceMsg", new AnnounceMsgListener());
    }

    public void fire(Response response) {
        String action = response.getAction();
        String resp = response.getResp();
        INotifyListener listener = map.get(action);
        if (listener == null) {
            Logger.t(TAG).d("no found notify listener");
            return;
        }

        NotifyClass notifyClass = listener.getClass().getAnnotation(NotifyClass.class);
        Class<?> clazz = notifyClass.value();
        Object result = null;
        try {
            result = new Gson().fromJson(resp, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        Logger.t(TAG).d(result);
        listener.fire(result);
    }
}