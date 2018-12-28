package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.activity.util.WebViewUtil
import com.cgy.chengy.demofactory.app.BaseActivity
import kotlinx.android.synthetic.main.activity_simple_web.*

/**
 * Created by ChenGY on 2018-12-26.
 */
class SimpleWebViewActivity : BaseActivity() {

    private lateinit var webView: WebView

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_web)
        initView()
    }

    private fun initView() {
        url = intent.getStringExtra(KEY_URL)

        iv_back.setOnClickListener {
            onBackPressed()
        }

        webView = wv.scrollView
        WebViewUtil.initFreshWebView(wv, webView, center_title)

        //如果是网址则显示，否则显示文字
        url?.let {
            if (url!!.startsWith("http")) {
                tv_nourl.visibility = View.GONE
                wv.visibility = View.VISIBLE
                webView.loadUrl(url)
            } else {
                tv_nourl.visibility = View.VISIBLE
                wv.visibility = View.GONE
                tv_nourl.text = url
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val KEY_URL = "urlStrKey"

        fun start(context: Context, urlStr: String) {
            val starter = Intent(context, SimpleWebViewActivity::class.java)
            starter.putExtra(KEY_URL, urlStr)
            context.startActivity(starter)
        }
    }

}