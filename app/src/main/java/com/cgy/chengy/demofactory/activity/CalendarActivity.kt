package com.cgy.chengy.demofactory.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity

class CalendarActivity : BaseActivity() {

    private lateinit var cv: CalendarView

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CalendarActivity::class.java)
            context.startActivity(starter)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        cv = findViewById(R.id.cv)
        cv.setDate(System.currentTimeMillis())
    }
}