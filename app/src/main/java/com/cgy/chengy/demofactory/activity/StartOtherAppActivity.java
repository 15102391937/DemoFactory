package com.cgy.chengy.demofactory.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;

public class StartOtherAppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_other_app);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn).setOnClickListener(v -> {
            if (checkPackInfo("com.outim.mechat")) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.outim.mechat", "com.outim.mechat..ui.activity.AuthorActivity");
                intent.setComponent(componentName);
                intent.putExtra("strKey", "StartOtherAppActivity");
                startActivity(intent);
            } else {
                Toast.makeText(this, "没有安装" + "", Toast.LENGTH_LONG).show();
                //下载操作
            }
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            if (checkPackInfo("com.outim.mechat")) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("yxoo://page/author?strKey=StartOtherAppActivity"));
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(this, "没有安装" + "", Toast.LENGTH_LONG).show();
                //下载操作
            }
        });
    }

    /**
     * 检查包是否存在
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, StartOtherAppActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
