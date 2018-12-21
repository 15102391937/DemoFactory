package com.cgy.chengy.demofactory.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import android.widget.Toast
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity

/**
 * Created by ChenGY on 2018-12-20.
 */
class MessengerActivity : BaseActivity() {

    private lateinit var clientMessenger: Messenger
    private var serviceMessenger: Messenger? = null
    private lateinit var serviceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        initData()
        initView()
    }

    private fun initData() {
        clientMessenger = Messenger(@SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                msg?.let {
                    if (msg.arg1 == 200) {
                        Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(mActivity, "失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                serviceMessenger = Messenger(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                serviceMessenger = null
            }
        }

        //绑定远程服务
        val intent = Intent()
        intent.component = ComponentName("com.cgy.chengy.demofactoryservice", "com.cgy.chengy.demofactoryservice.service.MessengerService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initView() {
        findViewById<View>(R.id.btn).setOnClickListener {
            val message = Message()
            message.arg1 = 1
            message.what = 10086
            message.data = Bundle().apply {
                putString("str", "fjdk")
            }
            message.replyTo = clientMessenger
            if (serviceMessenger == null) {
                Toast.makeText(mActivity, "serviceMessenger为空！", Toast.LENGTH_SHORT).show()
            }
            serviceMessenger?.send(message)
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MessengerActivity::class.java)
            context.startActivity(starter)
        }
    }
}