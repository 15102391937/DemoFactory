package com.cgy.chengy.demofactory.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import android.widget.Toast
import com.cgy.chengy.demofactory.app.BaseActivity
import com.cgy.chengy.demofactory.utils.AuthorConstants


/**
 * Created by ChenGY on 2018-12-20.
 */
class MessengerTwoActivity : BaseActivity() {

    private lateinit var clientMessenger: Messenger
    private var serviceMessenger: Messenger? = null
    private lateinit var serviceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.cgy.chengy.demofactory.R.layout.activity_messenger)
        initData()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun initData() {
        clientMessenger = Messenger(@SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                msg?.let {
                    if (msg.arg1 == AuthorConstants.MSG_ID_LOGIN) {
                        Toast.makeText(bActivity, "getCode：-- ${msg.data.getString(AuthorConstants.MSG_CODE)}", Toast.LENGTH_SHORT).show()

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
        intent.component = ComponentName("com.outim.mechat", "com.outim.mechat.receiver.YxSdkMessengerService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initView() {
        findViewById<View>(com.cgy.chengy.demofactory.R.id.btn).setOnClickListener {
            val message = Message()
            message.arg1 = AuthorConstants.MSG_ID_LOGIN
            message.data = Bundle().apply {
                putString(AuthorConstants.MSG_APPID, "ddddd")
            }
            message.replyTo = clientMessenger
            if (serviceMessenger == null) {
                Toast.makeText(bActivity, "serviceMessenger为空！", Toast.LENGTH_SHORT).show()
            }
            serviceMessenger?.send(message)
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MessengerTwoActivity::class.java)
            context.startActivity(starter)
        }
    }
}