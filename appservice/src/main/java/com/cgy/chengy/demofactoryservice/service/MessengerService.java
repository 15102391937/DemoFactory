package com.cgy.chengy.demofactoryservice.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ChenGY on 2018-12-20.
 */
public class MessengerService extends Service {

    @SuppressLint("HandlerLeak")
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //接收消息
            if (msg.what == 10086) {
                int arg1 = msg.arg1;
                String str = msg.getData().getString("str");
                Log.e("tag-msg.arg1", arg1 + "");
                Log.e("tag-msg.Data", str);

                //返回消息
                Messenger clientMessenger = msg.replyTo;
                if (clientMessenger != null) {
                    Message message = Message.obtain();
                    message.arg1 = 200;
                    try {
                        clientMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
