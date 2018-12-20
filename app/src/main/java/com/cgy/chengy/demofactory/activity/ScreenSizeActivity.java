package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;
import com.cgy.chengy.demofactory.utils.StrNumUtil;

public class ScreenSizeActivity extends BaseActivity {
    private TextView tv1, tv2, tv2_2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_size);
        initView();
    }

    private void initView() {
        TextView tv = findViewById(R.id.tv);
        StrNumUtil.ScreenWidthHeightUtil.showAllInTextView(tv);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv2_2 = findViewById(R.id.tv2_2);
        tv3 = findViewById(R.id.tv3);
        tv1.setText(getString(R.string.strs, "ffffff"));
        tv2.setText(getString(R.string.strd, 12345));
        tv2_2.setText(getString(R.string.strd2, 12345));
        tv3.setText(getString(R.string.strf, 1234.1234));
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ScreenSizeActivity.class);
        context.startActivity(starter);
    }
}
