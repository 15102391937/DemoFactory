package com.cgy.chengy.demofactory.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.chengy.demofactory.R;

import razerdp.basepopup.BasePopupWindow;

public class PopupTest1 extends BasePopupWindow {
    private Context context;

    public PopupTest1(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.popup_test1);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView textView1 = view.findViewById(R.id.tx_1);
        textView1.setOnClickListener(v -> {
            Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
            dismiss();
        });
    }

    @Override
    public void showPopupWindow() {
        PopupAniUtil.initTranslateAnimate(this, false);
        super.showPopupWindow();
    }

    @Override
    public void showPopupWindow(View anchorView) {
        PopupAniUtil.initTranslateAnimate(this, true);
        super.showPopupWindow(anchorView);
    }

}