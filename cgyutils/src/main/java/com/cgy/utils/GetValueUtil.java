package com.cgy.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;

/**
 * Created by ChenGY on 2017/8/1.
 */

public class GetValueUtil {

    public static Resources getRes(Context c) {
        return c.getResources();
    }

    public static String getString(Context c, int resId) {
        return getRes(c).getString(resId);
    }

    public static int getColor(Context c, int resId) {
        return getRes(c).getColor(resId);
    }

    /**
     * 和getDimension的功能类似，但是返回int.
     */
    public static int getDimensInt(Context c, int resId) {
        return getRes(c).getDimensionPixelOffset(resId);
    }

    /**
     * 单位是dp或sp，则需要将其乘以density
     * 如果是px，则不乘，返回float
     */
    public static float getDimensFloat(Context c, int resId) {
        return getRes(c).getDimension(resId);
    }

    /**
     * String转Color Int
     */
    public static int colorStr2ColorInt(String str) {
        int i = 0xffffff;
        if (!TextUtils.isEmpty(str)) {
            try {
                i = Color.parseColor(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

}
