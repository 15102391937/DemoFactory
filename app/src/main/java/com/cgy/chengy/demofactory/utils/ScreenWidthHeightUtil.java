package com.cgy.chengy.demofactory.utils;

import android.util.DisplayMetrics;
import android.widget.TextView;

import com.cgy.chengy.demofactory.app.App;

public class ScreenWidthHeightUtil {
    private static DisplayMetrics metrics;

    static {
        metrics = App.getInstance().getResources().getDisplayMetrics();
    }

    //屏幕高px
    public static int getHeightPixels() {
        return metrics.heightPixels;
    }

    //屏幕宽px
    public static int getWidthPixels() {
        return metrics.widthPixels;
    }

    //屏幕修正后的像素密度
    public static int getDensityDpi() {
        return metrics.densityDpi;
    }

    //屏幕物理X像素密度
    public static float getXdpi() {
        return metrics.xdpi;
    }

    //屏幕物理Y像素密度
    public static float getYdpi() {
        return metrics.ydpi;
    }

    //屏幕比例
    public static float getDensity() {
        return metrics.density;
    }

    //屏幕宽dp
    public static int getWidthDip() {
        return (int) (getWidthPixels() / getDensity());
    }

    //屏幕高dp
    public static int getHeightDip() {
        return (int) (getHeightPixels() / getDensity());
    }

    //显示他们
    public static void showAllInTextView(TextView textView) {
        StringBuilder builder = new StringBuilder();
        builder.append("WidthPixels--").append(getWidthPixels()).append("\r\n")
                .append("HeightPixels--").append(getHeightPixels()).append("\r\n")
                .append("WidthDip--").append(getWidthDip()).append("\r\n")
                .append("HeightDip--").append(getHeightDip()).append("\r\n")
                .append("DensityDpi--").append(getDensityDpi()).append("\r\n")
                .append("Xdpi--").append(getXdpi()).append("\r\n")
                .append("Ydpi--").append(getYdpi()).append("\r\n")
                .append("Density--").append(getDensity());
        textView.setText(builder.toString());
    }
}