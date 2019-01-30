package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.App
import com.cgy.chengy.demofactory.app.BaseActivity
import com.outim.yxopen.modelbase.SendAuth
import com.outim.yxopen.modelbase.YXConstants
import kotlinx.android.synthetic.main.activity_yxopen.*

/**
 * Created by ChenGY on 2018-12-20.
 */
class YxOpenActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yxopen)
        initView()
    }

    private fun initView() {
        btn.setOnClickListener {
            val req  = SendAuth.Req()
            req.state = "demofactoryState"
            req.scope = YXConstants.Scope.AUTHOR
            App.getInstance().yxapi.sendReq(req)
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, YxOpenActivity::class.java)
            context.startActivity(starter)
        }
    }
}