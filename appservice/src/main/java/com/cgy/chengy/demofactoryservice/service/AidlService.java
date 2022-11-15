package com.cgy.chengy.demofactoryservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.cgy.chengy.demofactoryservice.IRemoteService;

public class AidlService extends Service {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

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