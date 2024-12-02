package com.cgy.chengy.demofactory.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity
import com.cgy.chengy.demofactory.utils.DateUtil

class CalendarActivity : BaseActivity() {

    private lateinit var cv: CalendarView
    private lateinit var tvsbxb: TextView
    private lateinit var tvxztt: TextView
    private lateinit var etshagnban: EditText
    private lateinit var etxiaban: EditText
    private lateinit var etxizao: EditText
    private lateinit var ettangtou: EditText
    private var dateFormat = ""

    companion object {

        const val spKeyFront = "CA"
        const val spKeyFix_shagnban = "sb"
        const val spKeyFix_xiaban = "xb"
        const val spKeyFix_xizao = "xz"
        const val spKeyFix_tangtou = "tt"

        fun start(context: Context) {
            val starter = Intent(context, CalendarActivity::class.java)
            context.startActivity(starter)
        }
    }

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        cv = findViewById(R.id.cv)
        tvsbxb = findViewById(R.id.tvsbxb)
        tvxztt = findViewById(R.id.tvxztt)
        etshagnban = findViewById(R.id.etshagnban)
        etxiaban = findViewById(R.id.etxiaban)
        etxizao = findViewById(R.id.etxizao)
        ettangtou = findViewById(R.id.ettangtou)
        //赋值
        dateFormat = DateUtil.getCurrent_yMd1()
        cv.setDate(System.currentTimeMillis())
        etshagnban.setText(getSpData(spKeyFix_shagnban))
        etxiaban.setText(getSpData(spKeyFix_xiaban))
        etxizao.setText(getSpData(spKeyFix_xizao))
        ettangtou.setText(getSpData(spKeyFix_tangtou))
        setTvData()
        //监听
        cv.setOnDateChangeListener { cv, year, month, day ->
            dateFormat = String.format("%d%02d%02d", year, month + 1, day)
            etxizao.setText(getSpData(spKeyFix_xizao))
            ettangtou.setText(getSpData(spKeyFix_tangtou))
            etshagnban.setText(getSpData(spKeyFix_shagnban))
            etxiaban.setText(getSpData(spKeyFix_xiaban))
            setTvData()
        }
        etshagnban.addTextChangedListener(object : JustWa() {
            override fun afterTextChanged(p0: Editable?) {
                setSpData(spKeyFix_shagnban, p0.toString())
                setTvData()
            }
        })
        etxiaban.addTextChangedListener(object : JustWa() {
            override fun afterTextChanged(p0: Editable?) {
                setSpData(spKeyFix_xiaban, p0.toString())
                setTvData()
            }
        })
        etxizao.addTextChangedListener(object : JustWa() {
            override fun afterTextChanged(p0: Editable?) {
                setSpData(spKeyFix_xizao, p0.toString())
                setTvData()
            }
        })
        ettangtou.addTextChangedListener(object : JustWa() {
            override fun afterTextChanged(p0: Editable?) {
                setSpData(spKeyFix_tangtou, p0.toString())
                setTvData()
            }
        })
    }

    private fun setTvData() {
        tvsbxb.text = "上班：${getSpData(spKeyFix_shagnban)}\n" + "下班：${getSpData(spKeyFix_xiaban)}\n"
        tvxztt.text = "洗澡：${getSpData(spKeyFix_xizao)}\n" + "烫头：${getSpData(spKeyFix_tangtou)}\n"
    }

    private fun getSpKeyAll(key: String) = "$spKeyFront$dateFormat$key"

    private fun getSpData(key: String): String {
        return PreferenceManager.getDefaultSharedPreferences(bActivity).getString(getSpKeyAll(key), "") ?: ""
    }

    private fun setSpData(key: String, value: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(bActivity)
        val edit = sp.edit()
        edit.putString(getSpKeyAll(key), value)
        edit.apply()
    }
}

abstract class JustWa : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}