package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;
import com.cgy.chengy.demofactory.utils.StrNumUtil;

import java.util.ArrayList;
import java.util.List;

public class ListToArgsActivity extends BaseActivity {
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_to_args);
        initView();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        doSomething();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ListToArgsActivity.class);
        context.startActivity(starter);
    }

    private void doSomething() {
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("String" + i);
        }
        String[] array = list.toArray(new String[0]);
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            sb.append(s);
        }
        tv1.setText(sb.toString());
    }
}
