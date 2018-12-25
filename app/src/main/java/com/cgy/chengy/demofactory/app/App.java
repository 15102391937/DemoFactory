package com.cgy.chengy.demofactory.app;

import android.app.Application;

import com.cgy.chengy.demofactory.websocket.WsManager;
import com.orhanobut.logger.Logger;

/**
 * Created by ChenGY on 2017/7/25.
 */

public class App extends Application {
    private static volatile App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                if (mInstance == null) {
                    mInstance = new App();
                }
            }
        }
        return mInstance;
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
