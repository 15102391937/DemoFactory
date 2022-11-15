package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;

public class PopDemoActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv1, tv2, tv3,tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_demo);
        initView();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:

                break;
            case R.id.tv2:

                break;
            case R.id.tv3:

                break;
            case R.id.tv4:

                break;
            case R.id.tv5:

                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PopDemoActivity.class);
        context.startActivity(starter);
    }
}
