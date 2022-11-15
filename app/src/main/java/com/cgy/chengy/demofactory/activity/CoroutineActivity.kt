package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

/**
 * Created by ChenGY on 2018-12-20.
 */
class CoroutineActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        btn.setOnClickListener {
            btn.text = "${btn.text.toString().toInt() + 1}"
        }
        btn2.setOnClickListener {
            dealCoroutine()
        }
    }

    private fun dealCoroutine() {
        job.start()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    val job = launch(newFixedThreadPoolContext(2, "bg")) {
        Thread.sleep(6000L)
        launch(UI) {
            Toast.makeText(bActivity, "啦啦啦协程", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CoroutineActivity::class.java)
            context.startActivity(starter)
        }
    }
}