package com.cgy.chengy.demofactoryservice;

import android.app.Application;

/**
 * Created by ChenGY on 2017/7/25.
 */

public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
