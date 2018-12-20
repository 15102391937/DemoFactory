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
import com.cgy.chengy.demofactoryservice.IRemoteService;

public class AidlActivity extends BaseActivity {
    private IRemoteService iRemoteService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initData();
        initView();
    }

    private void initData() {//初始化
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iRemoteService = IRemoteService.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iRemoteService = null;
            }
        };

        //绑定远程服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.cgy.chengy.demofactoryservice", "com.cgy.chengy.demofactoryservice.DDService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        findViewById(R.id.btn).setOnClickListener(v -> {
            try {
                System.out.println("click-Thread: " + Thread.currentThread().getName());
                if (iRemoteService == null) {
                    Toast.makeText(mActivity, "iRemoteService为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                iRemoteService.getPid();
                iRemoteService.basicTypes(2, 333L, true, 3.4f, 4.33d, "fhadk");
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
        Intent starter = new Intent(context, AidlActivity.class);
        context.startActivity(starter);
    }
}
