package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity
import com.cgy.chengy.demofactory.popup.PopupAniUtil
import com.cgy.chengy.demofactory.popup.PopupTest1
import razerdp.basepopup.QuickPopupConfig
import razerdp.basepopup.QuickPopupBuilder
import razerdp.widget.QuickPopup
import java.security.AccessController.getContext


/**
 * Created by ChenGY on 2019-02-21.
 */
class PopupActivity : BaseActivity() {

    private var anchViewFlag = false
    private lateinit var pop: QuickPopup
    private lateinit var tvAn: TextView
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var tv4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)
        findViewById<TextView>(R.id.tv0).setOnClickListener { anchViewFlag = !anchViewFlag }
        tvAn = findViewById(R.id.tvAn)
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        tv3 = findViewById(R.id.tv3)
        tv4 = findViewById(R.id.tv4)
        tv1.setOnClickListener { showPop(1) }
        tv2.setOnClickListener { showPop(2) }
        tv3.setOnClickListener { showPop(3) }
        tv4.setOnClickListener {
            val gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            pop = QuickPopupBuilder.with(bActivity)
                    .contentView(R.layout.popup_test1)
                    .config(QuickPopupConfig()
                            .gravity(gravity)
                            .withShowAnimation(PopupAniUtil.initTranslateAnimateQuick(gravity, false, true))
                            .withDismissAnimation(PopupAniUtil.initTranslateAnimateQuick(gravity, false, false))
                            .withClick(R.id.tx_1) {
                                Toast.makeText(bActivity, "clicked", Toast.LENGTH_LONG).show()
                                pop.dismiss()
                            })
                    .show()
        }
    }

    private fun showPop(i: Int) {
        val popup = PopupTest1(bActivity)
        when (i) {
            1 -> popup.popupGravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            2 -> popup.popupGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            3 -> popup.popupGravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
            4 -> popup.popupGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        }
        if (anchViewFlag) popup.showPopupWindow(tvAn) else popup.showPopupWindow()
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PopupActivity::class.java)
            context.startActivity(starter)
        }
    }
}