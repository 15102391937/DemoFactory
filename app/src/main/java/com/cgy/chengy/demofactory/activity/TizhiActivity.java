package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;
import com.cgy.chengy.demofactory.photoview.PhotoView;
import com.cgy.chengy.demofactory.utils.DateUtil;
import com.cgy.chengy.demofactory.utils.GetValueUtil;
import com.cgy.chengy.demofactory.utils.StrNumUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class TizhiActivity extends BaseActivity {

    private double based1 = 0.0009929;
    private double based2 = 0.0000023;
    private double based3 = 0.0001392;
    private double based4 = 1.099421;
    private double based5 = 4.76;
    private double based6 = 4.28;
    private int baseage = 22;

    private double nvd1 = 0.0009929;
    private double nvd2 = 0.0000023;
    private double nvd3 = 0.0001392;
    private double nvd4 = 1.099421;
    private double nvd5 = 4.76;
    private double nvd6 = 4.28;
    private int nvage = 22;

    private double nand1 = 0.0008267;
    private double nand2 = 0.0000016;
    private double nand3 = 0.0002574;
    private double nand4 = 1.10938;
    private double nand5 = 4.97;
    private double nand6 = 4.52;
    private int nanage = 29;

    private Context mContext;
    private boolean isNv = true;
    private View container_pv;
    private PhotoView pv;
    private ImageView iv_xingbie;
    private EditText et1, et2, et3;
    private TextView tvresult1, tvresult2;
    private TextView tv_gongshi_midu, tv_gongshi_tizhi, tv_gongshi_age;
    private TextView tvweizhi1, tvweizhi2;
    private TextView tv_name1, tv_name2;
    private RecyclerView rv;
    private List<TizhiBean> mList;
    private TizhiAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, TizhiActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tizhi);
        mContext = this;
        initView();
        initListener();
        initDate();
    }

    private void initView() {
        pv = findViewById(R.id.pv);
        iv_xingbie = findViewById(R.id.iv_xingbie);
        container_pv = findViewById(R.id.container_pv);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        tvresult1 = findViewById(R.id.tv1);
        tvresult2 = findViewById(R.id.tv2);
        tv_gongshi_midu = findViewById(R.id.tv_gongshi_midu);
        tv_gongshi_tizhi = findViewById(R.id.tv_gongshi_tizhi);
        tv_gongshi_age = findViewById(R.id.tv_gongshi_age);
        tvweizhi1 = findViewById(R.id.tvweizhi1);
        tvweizhi2 = findViewById(R.id.tvweizhi2);
        tv_name1 = findViewById(R.id.tv_name1);
        tv_name2 = findViewById(R.id.tv_name2);
        rv = findViewById(R.id.rv);
    }

    private void initListener() {
        findViewById(R.id.btChange).setOnClickListener(view -> {
            clear();
            isNv = !isNv;
            changeSex();
        });
        findViewById(R.id.tvClear).setOnClickListener(view -> clear());
        findViewById(R.id.bt).setOnClickListener(view -> calculate());
        findViewById(R.id.bt_shili).setOnClickListener(view -> {
            container_pv.setVisibility(View.VISIBLE);
            pv.postDelayed(() -> pv.setScale(2.5f), 300);
        });
        findViewById(R.id.bt_back).setOnClickListener(view -> container_pv.setVisibility(View.GONE));
    }

    private void initDate() {
        pv.setMaximumScale(2.5f);
        pv.setImageResource(R.mipmap.tizhishili);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        isNv = (sp.getBoolean("sex", true));
        changeSex();
        et1.setText(sp.getString("str1", ""));
        et2.setText(sp.getString("str2", ""));
        et3.setText(sp.getString("str3", ""));
        mList = StrNumUtil.getEmptyList(new Gson().fromJson(sp.getString("rv", ""), new TypeToken<List<TizhiBean>>() {
        }.getType()));
        mAdapter = new TizhiAdapter();
        rv.setAdapter(mAdapter);
    }

    private void changeSex() {
        if (isNv) {
            tv_gongshi_midu.setText(GetValueUtil.getString(mContext, R.string.midu_nv));
            tv_gongshi_tizhi.setText(GetValueUtil.getString(mContext, R.string.tizhi_nv));
            tv_gongshi_age.setText("22");
            tvweizhi1.setText("肱三头肌");
            tvweizhi2.setText("髂前上棘");
            tv_name1.setText("乖乖的身体密度为：");
            tv_name2.setText("乖乖的体脂率为：");
            iv_xingbie.setImageResource(R.mipmap.qingtou_nv);
            based1 = nvd1;
            based2 = nvd2;
            based3 = nvd3;
            based4 = nvd4;
            based5 = nvd5;
            based6 = nvd6;
            baseage = nvage;
        } else {
            tv_gongshi_midu.setText(GetValueUtil.getString(mContext, R.string.midu_nan));
            tv_gongshi_tizhi.setText(GetValueUtil.getString(mContext, R.string.tizhi_nan));
            tv_gongshi_age.setText("29");
            tvweizhi1.setText("胸");
            tvweizhi2.setText("腹");
            tv_name1.setText("陈同学的身体密度为：");
            tv_name2.setText("陈同学的体脂率为：");
            iv_xingbie.setImageResource(R.mipmap.qingtou_nan);
            based1 = nand1;
            based2 = nand2;
            based3 = nand3;
            based4 = nand4;
            based5 = nand5;
            based6 = nand6;
            baseage = nanage;
        }
    }

    private void clear() {
        et1.setText("");
        et2.setText("");
        et3.setText("");
        tvresult1.setText("");
        tvresult2.setText("");
    }

    private void calculate() {
        try {
            String str1 = et1.getText().toString();
            String str2 = et2.getText().toString();
            String str3 = et3.getText().toString();
            BigDecimal bd1 = new BigDecimal(str1);
            BigDecimal bd2 = new BigDecimal(str2);
            BigDecimal bd3 = new BigDecimal(str3);
            BigDecimal he = bd1.add(bd2).add(bd3);
            BigDecimal temp1 = new BigDecimal(based1).multiply(he);
            BigDecimal temp2 = new BigDecimal(based2).multiply(he.pow(2));
            BigDecimal temp3 = new BigDecimal(based3).multiply(new BigDecimal(baseage));
            BigDecimal result1 = new BigDecimal(based4).subtract(temp1).add(temp2).add(temp3);
            String resultMidu = StrNumUtil.keepXDecimal(result1, 2);
            tvresult1.setText(resultMidu);
            BigDecimal result2 = new BigDecimal(based5).divide(result1, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(based6)).multiply(new BigDecimal(100));
            String resultTizhi = StrNumUtil.keepXDecimal(result2, 2);
            tvresult2.setText(resultTizhi);
            String name = "梁老师";
            if (!isNv) name = "陈同学";
            mList.add(0, new TizhiBean(DateUtil.getCurrent_Md(), name, str1, str2, str3, resultMidu, resultTizhi));
            mAdapter.notifyDataSetChanged();
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("sex", isNv);
            edit.putString("str1", str1);
            edit.putString("str2", str2);
            edit.putString("str3", str3);
            edit.putString("rv", new Gson().toJson(mList));
            edit.apply();
        } catch (Exception e) {
            Toast.makeText(mContext, "憨憨，数据填错啦", Toast.LENGTH_SHORT).show();
        }
    }

    // 定义一个变量，来标识是否退出
    private Boolean isExit = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (container_pv.getVisibility() == View.VISIBLE) {
            container_pv.setVisibility(View.GONE);
        } else {
            if (isExit) {
                finish();
                System.exit(0);
            } else {
                isExit = true;
                Toast.makeText(mContext, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    }

    private class TizhiAdapter extends RecyclerView.Adapter<TizhiAdapter.ViewHolder> {

        @NotNull
        @Override
        public TizhiAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tizhi, parent, false);
            return new TizhiAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NotNull TizhiAdapter.ViewHolder holder, int position) {
            TizhiBean bean = mList.get(position);
            holder.tv_time.setText(bean.time);
            holder.tv_name.setText(bean.name);
            holder.tv_weidu1.setText(bean.weidu1);
            holder.tv_weidu2.setText(bean.weidu2);
            holder.tv_weidu3.setText(bean.weidu3);
            holder.tv_midu.setText(bean.midu);
            holder.tv_tizhi.setText(bean.tizhi);
            if (bean.name.equals("梁老师")) {
                holder.tv_name.setTextColor(GetValueUtil.getColor(mContext, R.color.bg_color_item_room));
            } else {
                holder.tv_name.setTextColor(GetValueUtil.getColor(mContext, R.color.base_color_press_blue_white));
            }
            holder.bt_delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("确定删除" + bean.time + "的记录哇~")
                        .setNegativeButton("确定哦~", (dialogInterface, i) -> {
                            mList.remove(position);
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("rv", new Gson().toJson(mList));
                            edit.apply();
                            notifyDataSetChanged();
                        })
                        .setPositiveButton("点错啦~", null)
                        .create()
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_time;
            TextView tv_name;
            TextView tv_weidu1;
            TextView tv_weidu2;
            TextView tv_weidu3;
            TextView tv_midu;
            TextView tv_tizhi;
            TextView bt_delete;

            ViewHolder(final View view) {
                super(view);
                tv_time = view.findViewById(R.id.tv_time);
                tv_name = view.findViewById(R.id.tv_name);
                tv_weidu1 = view.findViewById(R.id.tv_weidu1);
                tv_weidu2 = view.findViewById(R.id.tv_weidu2);
                tv_weidu3 = view.findViewById(R.id.tv_weidu3);
                tv_midu = view.findViewById(R.id.tv_midu);
                tv_tizhi = view.findViewById(R.id.tv_tizhi);
                bt_delete = view.findViewById(R.id.bt_delete);
            }
        }
    }

    private class TizhiBean {

        public TizhiBean(String time, String name, String weidu1, String weidu2, String weidu3, String midu, String tizhi) {
            this.time = time;
            this.name = name;
            this.weidu1 = weidu1;
            this.weidu2 = weidu2;
            this.weidu3 = weidu3;
            this.midu = midu;
            this.tizhi = tizhi;
        }

        String time;
        String name;
        String weidu1;
        String weidu2;
        String weidu3;
        String midu;
        String tizhi;
    }
}
