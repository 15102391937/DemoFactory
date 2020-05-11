package com.cgy.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ChenGY on 2017/10/27.
 */
public class StrNumUtil {

    //region Str To Other

    /**
     * String转Int
     */
    public static int Str2Int(String str, int need) {
        int i = need;
        if (!TextUtils.isEmpty(str)) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    /**
     * String转Float
     */
    public static float Str2Float(String str, float need) {
        float f = need;
        if (!TextUtils.isEmpty(str)) {
            try {
                f = Float.parseFloat(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    /**
     * String转Double
     */
    public static double Str2Double(String str, double need) {
        double d = need;
        if (!TextUtils.isEmpty(str)) {
            try {
                d = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    /**
     * String转Long
     */
    public static long Str2Long(String str, long need) {
        long l = need;
        if (!TextUtils.isEmpty(str)) {
            try {
                l = Long.parseLong(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return l;
    }

    /**
     * String转Int 默认值0
     */
    public static int Str2Int(String str) {
        return Str2Int(str, 0);
    }

    /**
     * String转Float 默认值0f
     */
    public static float Str2Float(String str) {
        return Str2Float(str, 0f);
    }

    /**
     * String转Double 默认值0d
     */
    public static double Str2Double(String str) {
        return Str2Double(str, 0d);
    }

    /**
     * String转Long 默认值0l
     */
    public static long Str2Long(String str) {
        return Str2Long(str, 0L);
    }

    /**
     * String转Int
     */
    public static int DoubleStr2Int(String str) {
        int i = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                double d = Double.parseDouble(str);
                i = (int) d;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //endregion

    //region empty

    /**
     * 判空获取值
     */
    public static String getEmptyStr(Object obj) {
        String result = "";
        if (obj != null) {
            String str = obj.toString();
            if (!TextUtils.isEmpty(str)) {
                result = str;
            }
        }
        return result;
    }

    /**
     * 判空获取值（需要的）
     */
    public static String getEmptyStr(Object obj, String need) {
        String result = need;
        if (obj != null) {
            String str = obj.toString();
            if (!TextUtils.isEmpty(str)) {
                result = str;
            }
        }
        return result;
    }

    /**
     * 判空获取值
     */
    public static String getEmptyStr(String str) {
        String result = "";
        if (!TextUtils.isEmpty(str)) {
            result = str;
        }
        return result;
    }

    /**
     * 判空获取值（需要的）
     */
    public static String getEmptyStr(String str, String need) {
        String result = need;
        if (!TextUtils.isEmpty(str)) {
            result = str;
        }
        return result;
    }

    /**
     * 截取值防报错
     */
    public static String getSubStr(String str, int start, int end) {
        String result = "";
        try {
            result = str.substring(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 安全获取list
     */
    public static <T> List<T> getEmptyList(List<T> list) {
        List<T> result = new ArrayList<T>();
        if (list != null) {
            result = list;
        }
        return result;
    }

    //endregion

    //region other

    /**
     * 比较两个long字符串的大小
     */
    public static long compareTwoStringLong(String str1, String str2) {
        return Str2Long(str1) - Str2Long(str2);
    }

    /**
     * 比较两个long字符串的大小
     */
    public static long compareTwoStringLong2(String str1, long l) {
        return Str2Long(str1) - l;
    }

    /**
     * 简单的隐藏一位小数.0
     */
    public static String formatMoney(String money) {
        String result = "0";
        if (!TextUtils.isEmpty(money)) {
            if (money.endsWith(".0")) {
                result = money.substring(0, money.length() - 2);
            }
        }
        return result;
    }

    /**
     * list判空
     */
    public static boolean notEmptyList(List list) {
        return list != null && list.size() > 0;
    }

    /**
     * list判空
     */
    public static boolean isEmptyList(List list) {
        return !notEmptyList(list);
    }

    /**
     * list兼容获取前X项
     */
    public static <T> List<T> safeSublist(List<T> list, int x) {
        List<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int getNum = list.size();
            if (getNum > x) getNum = x;
            result.addAll(list.subList(0, getNum));
        }
        return result;
    }

    /**
     * 将数字转换成字母(0代表A)
     */
    public static String numToLetter(int num) {
        return (char) (num + 65) + "";
    }

    /**
     * 俩int数据相除得float
     */
    public static float twoIntdivideFloat(int a, int b) {
        return ((float) a) / ((float) b);
    }

    /**
     * 俩String 格式int数据相乘
     */
    public static String twoIntStrMutipyInt(String a, String b) {
        return Str2Int(a) * Str2Int(b) + "";
    }

    /**
     * 俩double字符串数据相乘得int字符串
     */
    public static String twoDoubleStrMultipyInt(String str1, String str2) {
        return (int) (Str2Double(str1) * Str2Double(str2)) + "";
    }

    /**
     * 俩double字符串数据相乘,保留两位小数
     */
    public static String twoDoubleStrMultipy(String str1, String str2) {
        return keepTwoDecimal(Str2Double(str1) * Str2Double(str2));
    }

    /**
     * 俩double字符串数据相乘,保留两位小数（百分比）
     */
    public static String twoDoubleStrMultipy2(String str1, String str2) {
        return keepTwoDecimal(Str2Double(str1) * Str2Double(str2) / 100);
    }

    /**
     * 比较两个double字符串的大小
     */
    public static double compareTwoStringDouble(String str1, String str2) {
        return Str2Double(str1) - Str2Double(str2);
    }

    /**
     * 比较两个double字符串的大小2
     */
    public static double compareTwoStringDouble2(String str1, String str2) {
        return Str2Double(str1) - Str2Double(str2) * 10000;
    }

    //endregion

    //region money

    /**
     * 保留两位小数——四舍五入
     */
    public static float roundNormalTwoPlace(float res) {
        try {
            return new BigDecimal(res).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 取整——四舍五入
     */
    public static String roundNormalZeroPlace(String res) {
        try {
            return new BigDecimal(res).setScale(0, BigDecimal.ROUND_UP).toString();
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 保留两位小数——进位
     */
    public static double roundUpTwoPlace(double res) {
        try {
            return new BigDecimal(res).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 保留两位小数——进位
     */
    public static float roundUpTwoPlace(float res) {
        try {
            return new BigDecimal(res).setScale(2, BigDecimal.ROUND_UP).floatValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 保留两位小数——舍位
     */
    public static double roundDownTwoPlace(double res) {
        try {
            return new BigDecimal(res).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 保留两位小数——舍位
     */
    public static float roundDownTwoPlace(float res) {
        try {
            return new BigDecimal(res).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
        } catch (Exception e) {
            return 0;
        }
    }

    //endregion

    //region split

    /**
     * 分割字符串只要前一个
     */
    public static String splitFront(String res, String reg) {
        String result = "";
        if (!TextUtils.isEmpty(res)) {
            String[] strs = res.split(reg);
            if (strs.length == 0) {//有reg且reg是末尾，也就是res == reg
            } else {
                if (strs.length == 1) {//没有reg || 空串 || 末尾是reg
                    if (!res.contains(reg)) {//没有reg || 空串
                    } else {//末尾是reg
                    }
                } else {//有reg且reg不是末尾，reg前面可以没有值
                }
                result = strs[0];
            }
        }
        return result;
    }

    /**
     * 分割字符串只要后一个
     */
    public static String splitBehind(String res, String reg) {
        String result = "";
        if (!TextUtils.isEmpty(res)) {
            String[] strs = res.split(reg);
            if (strs.length == 0) {//有reg且reg是末尾，也就是res == reg
            } else {
                if (strs.length == 1) {//没有reg || 空串 || 末尾是reg
                    if (!res.contains(reg)) {//没有reg || 空串
                    } else {//末尾是reg
                    }
                } else {//有reg且reg不是末尾，reg前面可以没有值
                    result = strs[1];
                }
            }
        }
        return result;
    }

    //endregion

    //region For EventBus

    public static String eventBusJoint(int a, int b) {
        return a + "w-w" + b;
    }

    public static String eventBusJoint(String a, String b) {
        return a + "w-w" + b;
    }

    public static String[] eventBusSplit(String s) {
        return s.split("w-w");
    }

    //endregion

    //region 保留固定位数小数

    /**
     * 保留X位小数
     */
    public static String keepXDecimal(Double res, int place) {
        StringBuilder pattern = new StringBuilder("0");
        if (place > 0) {
            for (int i = 0; i < place; i++) {
                if (i == 0) {
                    pattern.append(".0");
                } else {
                    pattern.append("0");
                }
            }
        }
        return new DecimalFormat(pattern.toString()).format(res);
    }

    /**
     * 保留一位小数
     */
    public static String keepOneDecimal(String res) {
        return keepXDecimal(Str2Double(res), 1);
    }

    /**
     * 保留两位小数
     */
    public static String keepTwoDecimal(Double res) {
        return keepXDecimal(res, 2);
    }

    /**
     * 保留两位小数
     */
    public static String keepTwoDecimal(String res) {
        return keepTwoDecimal(Str2Double(res));
    }

    /**
     * 保留两位小数
     */
    public static String keepTwoDecimal(Float res) {
        return keepTwoDecimal(res.doubleValue());
    }

    /**
     * 保留八位小数
     */
    public static String keepEightDecimal(Double res) {
        return keepXDecimal(res, 8);
    }

    /**
     * 保留八位小数
     */
    public static String keepEightDecimal(String res) {
        return keepEightDecimal(Str2Double(res));
    }

    /**
     * 保留整数，并每隔3位一个分隔符
     */
    public static String keepZeroDecimalAddDouhao(String res) {
        return new DecimalFormat("###,###").format(Str2Int(res));
    }

    /**
     * 除以10000后，保留两位小数
     */
    public static String keepTwoDecimalDivideWan(Float res) {
        return keepTwoDecimal(res / 10000f);
    }

    /**
     * 除以10000后，保留两位小数
     */
    public static String keepTwoDecimalDivideWan(int res) {
        return keepTwoDecimal(res / 10000f);
    }

    /**
     * 除以10000后，保留两位小数
     */
    public static String keepTwoDecimalDivideWan(String res) {
        return keepTwoDecimalDivideWan(Str2Float(res));
    }

    /**
     * 除以1000保留整数(13位时间戳转10位)
     */
    public static String divideQian(String res) {
        return (int) (Str2Float(res) / 1000f) + "";
    }

    /**
     * 乘以10000
     */
    public static String multiWan(String res) {
        return String.valueOf(Str2Float(res) * 10000f);
    }

    //endregion

    //region other more

    public static String booleanToStr10(boolean b) {
        return b ? "1" : "0";
    }

    public static String booleanToStr12(boolean b) {
        return b ? "1" : "2";
    }

    public static boolean str1ToBoolean(String s) {
        return Objects.equals(s, "1");
    }

    //endregion
}