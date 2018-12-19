package com.cgy.chengy.demofactory;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cgy.chengy.demofactoryservice.IRemoteService;

public class MainActivity extends AppCompatActivity {
    private TextView tv1, tv2, tv2_2, tv3;
    private IRemoteService iRemoteService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.tv);
        ScreenWidthHeightUtil.showAllInTextView(tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2_2 = (TextView) findViewById(R.id.tv2_2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv1.setText(getString(R.string.strs, "ffffff"));
        tv2.setText(getString(R.string.strd, 12345));
        tv2_2.setText(getString(R.string.strd2, 12345));
        tv3.setText(getString(R.string.strf, 1234.1234));
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("click-Thread: " + Thread.currentThread().getName());
                    iRemoteService.getPid();
                    iRemoteService.basicTypes(2, 333L, true, 3.4f, 4.33d, "fhadk");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
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
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.cgy.chengy.demofactoryservice", "com.cgy.chengy.demofactoryservice.DDService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);//绑定远程服务
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
