package com.cgy.chengy.demofactoryservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DDService extends Service {
    private IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        public int getPid() {
            Log.e("tag", "Thread: " + Thread.currentThread().getName());
            return 1;
        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
            Log.e("tag", "Thread: " + Thread.currentThread().getName());
            Log.e("tag", "basicTypes aDouble: " + aDouble + " anInt: " + anInt + " aBoolean " + aBoolean + " aString " + aString);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }
}