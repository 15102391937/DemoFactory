package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bin.david.form.core.TableConfig
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat
import com.bin.david.form.data.table.TableData
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity
import com.cgy.chengy.demofactory.utils.GetValueUtil
import kotlinx.android.synthetic.main.activity_smarttable.*


/**
 * Created by ChenGY on 2018-12-20.
 */
class SmartTableActivity : BaseActivity() {

    val userList = arrayListOf(
            User("高级", 7.143251f, 5.3434f, 7.1432561f, 5.34134f, 7.1432561f, 5.34134f, "+"),
            User("中级", 6.143251f, 4.3434f, 6.1432561f, 4.34134f, 6.1432561f, 4.34134f, "+"),
            User("低级", 5.143251f, 3.3434f, 5.1432561f, 3.34134f, 5.1432561f, 3.34134f, "+"),
            User("垃圾", 4.143251f, 2.3434f, 4.1432561f, 2.34134f, 4.1432561f, 2.34134f, "+"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smarttable)
        tv.setOnClickListener {
            val xx = userList
        }
        initTahleView()
    }

    private fun initTahleView() {
        //普通列
        val columnGradle = Column<String>("", "name").apply { isFixed = true }
        val column1 = Column<Float>("1层", "rate1")
        val column2 = Column<Float>("2层", "rate2")
        val column3 = Column<Float>("3层", "rate3")
        val column4 = Column<Float>("4层", "rate4")
        val column5 = Column<Float>("5层", "rate5")
        val column6 = Column<Float>("6层", "rate6")
        val columnAdd = Column<String>("", "addSymbol")
        //设置
        table.config.apply {
            isShowTableTitle = false
            isShowXSequence = false
            isShowYSequence = false
            minTableWidth = 900
            contentCellBackgroundFormat = object : BaseCellBackgroundFormat<CellInfo<Any>>() {
                override fun getBackGroundColor(cellInfo: CellInfo<Any>): Int {
                    return if (cellInfo.row % 2 == 0) {
                        GetValueUtil.getColor(bActivity, R.color.gray_e3)
                    } else TableConfig.INVALID_COLOR
                }
            }

        }
        //表格数据 datas是需要填充的数据
        val tableData = TableData<User>("表格名", userList, columnGradle, column1, column2, column3, column4, column5, column6, columnAdd)
        //点击事件
        tableData.setOnItemClickListener { column, value, t, col, row ->
            if (col == 7) {
                Toast.makeText(bActivity, "佳佳佳佳", Toast.LENGTH_SHORT).show()
                return@setOnItemClickListener
            }
            column.datas[row]  = 1000f
            userList[row].rate2 = 1000f
            table.invalidate()
        }
        table.tableData = tableData
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SmartTableActivity::class.java)
            context.startActivity(starter)
        }
    }

    class User(
            val name: String,
            var rate1: Float,
            var rate2: Float,
            var rate3: Float,
            var rate4: Float,
            var rate5: Float,
            var rate6: Float,
            val addSymbol: String
    )
}