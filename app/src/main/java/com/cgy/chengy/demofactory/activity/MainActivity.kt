package com.cgy.chengy.demofactory.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> ScreenSizeActivity.start(bActivity)
            R.id.tv2 -> AidlActivity.start(bActivity)
            R.id.tv3 -> MessengerActivity.start(bActivity)
            R.id.tv4 -> ScreenSizeActivity.start(bActivity)
            R.id.tv5 -> WebGoActivity.start(bActivity)
            R.id.tv6 -> YxOpenActivity.start(bActivity)
            R.id.tv7 -> PopupActivity.start(bActivity)
            R.id.tvr_1 -> ParseUrlActivity.start(bActivity)
            R.id.tvr_2 -> AidlTwoActivity.start(bActivity)
            R.id.tvr_3 -> doSomeTest()
            R.id.tvr_4 -> MessengerTwoActivity.start(bActivity)
            R.id.tvr_5 -> StartOtherAppActivity.start(bActivity)
            R.id.tvr_6 -> CoroutineActivity.start(bActivity)
            R.id.tvr_7 -> SmartTableActivity.start(bActivity)
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
        findViewById<View>(R.id.tv6).setOnClickListener(this)
        findViewById<View>(R.id.tv7).setOnClickListener(this)
        findViewById<View>(R.id.tvr_1).setOnClickListener(this)
        findViewById<View>(R.id.tvr_2).setOnClickListener(this)
        findViewById<View>(R.id.tvr_3).setOnClickListener(this)
        findViewById<View>(R.id.tvr_4).setOnClickListener(this)
        findViewById<View>(R.id.tvr_5).setOnClickListener(this)
        findViewById<View>(R.id.tvr_6).setOnClickListener(this)
        findViewById<View>(R.id.tvr_7).setOnClickListener(this)
    }

    private fun doSomeTest() {
        var result = 0
        for (i in 0..10) {
            result += i
        }
    }

}
