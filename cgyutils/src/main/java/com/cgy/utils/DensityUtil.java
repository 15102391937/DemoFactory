package com.cgy.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Created by ChenGY on 2019-01-30.
 */
public class DensityUtil {
    private static float sNonCompatDensity;//不兼容的Density
    private static float sNonCompatScaledDensity;//不兼容的字体Density
    private static DisplayMetrics appDm;//app的DM，用于获取屏幕宽度，以及原始的Density
    private static final float DESIGN_WIDTH = 375f;//设计图的设计尺寸（其实不用管单位，要的是比例）
    /**
     * 获取app的DM，以及系统原始的不兼容的Density，在Application的onCreate方法中调用
     */
    public static void initNonCompatDensity(final @NonNull Application application) {
        appDm = application.getResources().getDisplayMetrics();
        //如果系统原始的未兼容的Density为零，那么获取它，由于是static的，所以判断sNonCompatDensity是否为零，只获取一次
        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDm.density;
            sNonCompatScaledDensity = appDm.scaledDensity;
            //系统设置中切换字体，再返回应用，字体并没有变化。于是还得监听下字体切换
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {
                }
            });
        }
    }
    /**
     * 设置Activity以及下面的Fragment、PopWindow、Dialog等View的Density
     * 在BaseActivity的setContentView(layoutId)之前使用;
     */
    public static void setActCustomDensity(Activity activity) {
        //适配宽为360dp的设计图，计算目标 density、densityDpi
        final float targetDensity = appDm.widthPixels / DESIGN_WIDTH;
        final int targetDensityDpi = (int) (targetDensity * 160f);
        //通过计算scaledDensity和density的比获得现在的scaledDensity
        final float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        //获取activity的DisplayMetrics，并将其设置为目标值
        final DisplayMetrics actDm = activity.getResources().getDisplayMetrics();
        actDm.density = targetDensity;
        actDm.densityDpi = targetDensityDpi;
        actDm.scaledDensity = targetScaledDensity;
    }
}
