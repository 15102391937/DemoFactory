package com.cgy.chengy.demofactory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv1, tv2,tv2_2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {//初始化
        TextView tv = (TextView) findViewById(R.id.tv);
//        ScreenWidthHeightUtil.showAllInTextView(tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2_2 = (TextView) findViewById(R.id.tv2_2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv1.setText(getString(R.string.strs, "ffffff"));
        tv2.setText(getString(R.string.strd, 12345));
        tv2_2.setText(getString(R.string.strd2, 12345));
        tv3.setText(getString(R.string.strf, 1234.1234));
    }
}
