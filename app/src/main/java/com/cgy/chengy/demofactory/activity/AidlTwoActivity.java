package com.cgy.chengy.demofactory.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;
import com.outim.mechat.YxSdkAidl;

public class AidlTwoActivity extends BaseActivity {
    private YxSdkAidl yxSdkAidl;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl2);
        initData();
        initView();
    }

    private void initData() {//初始化
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                yxSdkAidl = YxSdkAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                yxSdkAidl = null;
            }
        };

        //绑定远程服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.outim.mechat", "com.outim.mechat.receiver.YxSdkAidlService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        findViewById(R.id.btn).setOnClickListener(v -> {
            try {
                if (yxSdkAidl == null) {
                    Toast.makeText(bActivity, "yxSdkAidl为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                yxSdkAidl.startAuthorAct();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AidlTwoActivity.class);
        context.startActivity(starter);
    }
}
