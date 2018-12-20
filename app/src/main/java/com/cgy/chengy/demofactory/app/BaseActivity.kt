package com.cgy.chengy.demofactory.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by ChenGY on 2018-12-20.
 */
open class BaseActivity: AppCompatActivity(){

    lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this
        super.onCreate(savedInstanceState)
    }

}