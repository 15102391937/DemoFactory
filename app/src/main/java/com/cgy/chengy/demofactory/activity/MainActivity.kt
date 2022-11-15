package com.cgy.chengy.demofactory.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity

class MainActivity : BaseActivity(), View.OnClickListener {
    lateinit var activity: Activity

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> ScreenSizeActivity.start(bActivity)
            R.id.tv2 -> AidlActivity.start(bActivity)
            R.id.tv3 -> MessengerActivity.start(bActivity)
            R.id.tv4 -> ScreenSizeActivity.start(bActivity)
            R.id.tv5 -> WebGoActivity.start(bActivity)
            R.id.tv6 -> YxOpenActivity.start(bActivity)
            R.id.tv7 -> PopupActivity.start(bActivity)
            R.id.tv8 -> ListToArgsActivity.start(bActivity)
            R.id.tvr_1 -> ParseUrlActivity.start(bActivity)
            R.id.tvr_2 -> AidlTwoActivity.start(bActivity)
            R.id.tvr_3 -> doSomeTest()
            R.id.tvr_4 -> MessengerTwoActivity.start(bActivity)
            R.id.tvr_5 -> StartOtherAppActivity.start(bActivity)
            R.id.tvr_6 -> CoroutineActivity.start(bActivity)
            R.id.tvr_7 -> SmartTableActivity.start(bActivity)
            R.id.tvr_8 -> TizhiActivity.start(bActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this
        initView()
        initGameShotNeed()
    }

    private fun initView() {
        findViewById<View>(R.id.tv1).setOnClickListener(this)
        findViewById<View>(R.id.tv2).setOnClickListener(this)
        findViewById<View>(R.id.tv3).setOnClickListener(this)
        findViewById<View>(R.id.tv4).setOnClickListener(this)
        findViewById<View>(R.id.tv5).setOnClickListener(this)
        findViewById<View>(R.id.tv6).setOnClickListener(this)
        findViewById<View>(R.id.tv7).setOnClickListener(this)
        findViewById<View>(R.id.tv8).setOnClickListener(this)
        findViewById<View>(R.id.tvr_1).setOnClickListener(this)
        findViewById<View>(R.id.tvr_2).setOnClickListener(this)
        findViewById<View>(R.id.tvr_3).setOnClickListener(this)
        findViewById<View>(R.id.tvr_4).setOnClickListener(this)
        findViewById<View>(R.id.tvr_5).setOnClickListener(this)
        findViewById<View>(R.id.tvr_6).setOnClickListener(this)
        findViewById<View>(R.id.tvr_7).setOnClickListener(this)
        findViewById<View>(R.id.tvr_8).setOnClickListener(this)
    }

    private fun doSomeTest() {
        var result = 0
        for (i in 0..10) {
            result += i
        }
    }

    var seekbar_progress: SeekBar? = null
    var seekbar_progress_tv: TextView? = null

    private fun initGameShotNeed() {
        seekbar_progress = findViewById(R.id.seekbar_progress)
        seekbar_progress_tv = findViewById(R.id.seekbar_progress_tv)
        seekbar_progress?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekbar_progress_tv?.setText(progress.toString() + "")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        seekbar_progress_tv?.setOnClickListener {
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                }
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_DENIED -> {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE), 1)//请求权限
                }
                else -> {
                    GameShotActivity.start(activity, Integer.parseInt((seekbar_progress_tv!!.text.toString())))
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GameShotActivity.start(activity, Integer.parseInt((seekbar_progress_tv!!.text.toString())))
                }
            }
        }
    }
}
