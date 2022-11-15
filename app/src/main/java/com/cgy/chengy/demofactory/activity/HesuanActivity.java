package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;


public class HesuanActivity extends BaseActivity {

    private Context mContext;

    public static void start(Context context) {
        Intent starter = new Intent(context, HesuanActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_hesuan);
    }

}
