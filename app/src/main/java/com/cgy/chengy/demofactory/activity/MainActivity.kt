package com.cgy.chengy.demofactory.activity

import android.os.Bundle
import android.view.View
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> ScreenSizeActivity.start(mActivity)
            R.id.tv2 -> AidlActivity.start(mActivity)
            R.id.tv3 -> MessengerActivity.start(mActivity)
            R.id.tv4 -> ScreenSizeActivity.start(mActivity)
            R.id.tv5 -> WebGoActivity.start(mActivity)
            R.id.tvr_1 -> ParseUrlActivity.start(mActivity)
            R.id.tvr_2 -> ScreenSizeActivity.start(mActivity)
            R.id.tvr_3 -> ScreenSizeActivity.start(mActivity)
            R.id.tvr_4 -> ScreenSizeActivity.start(mActivity)
            R.id.tvr_5 -> ScreenSizeActivity.start(mActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        findViewById<View>(R.id.tv1).setOnClickListener(this)
        findViewById<View>(R.id.tv2).setOnClickListener(this)
        findViewById<View>(R.id.tv3).setOnClickListener(this)
        findViewById<View>(R.id.tv4).setOnClickListener(this)
        findViewById<View>(R.id.tv5).setOnClickListener(this)
        findViewById<View>(R.id.tvr_1).setOnClickListener(this)
        findViewById<View>(R.id.tvr_2).setOnClickListener(this)
        findViewById<View>(R.id.tvr_3).setOnClickListener(this)
        findViewById<View>(R.id.tvr_4).setOnClickListener(this)
        findViewById<View>(R.id.tvr_5).setOnClickListener(this)
    }
}
