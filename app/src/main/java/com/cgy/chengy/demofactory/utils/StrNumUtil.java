package com.cgy.chengy.demofactory.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.cgy.chengy.demofactory.app.App;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
    public static int getZeroInt(int i, int need) {
        return i == 0 ? need : i;
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
    public static String getEmptyStr(Object obj) {
        return getEmptyStr(obj, "");
    }

    /**
     * 截取值防报错
     */
    public static String getSubStr(String str, int start, int end) {
        String result = str;
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
    public static <T> boolean isEmptyList(Collection<T> collection) {
        return collection == null || collection.size() <= 0;
    }

    /**
     * list判空
     */
    public static <T> boolean notEmptyList(Collection<T> collection) {
        return !isEmptyList(collection);
    }

    /**
     * list兼容获取前X项
     */
    public static <T> List<T> safeSublist(List<T> list, int x) {
        List<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int totalSize = list.size();
            if (x > totalSize) x = totalSize;
            result.addAll(list.subList(0, x));
        }
        return result;
    }

    /**
     * list兼容获取 以p为基准，取后X项，如果不够，则往前补足
     */
    public static <T> ArrayList<T> safeSublistBaseP(List<T> list, int position, int number) {
        ArrayList<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int totalSize = list.size();
            if (number > totalSize) {
                result.addAll(list);
            } else {
                if (position + number > totalSize) {
                    result.addAll(list.subList(totalSize - number, position));
                    result.addAll(list.subList(position, totalSize));
                } else {
                    result.addAll(list.subList(position, position + number));
                }
            }
        }
        return result;
    }

    /**
     * list兼容获取 取后X项
     */
    public static <T> ArrayList<T> safeSublistEndX(List<T> list, int x) {
        ArrayList<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int totlaSize = list.size();
            if (x > totlaSize) x = totlaSize;
            result.addAll(list.subList(totlaSize - x, totlaSize));
        }
        return result;
    }

    /**
     * list兼容获取 从X项开始
     */
    public static <T> ArrayList<T> safeSublistFromX(List<T> list, int x) {
        ArrayList<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int totlaSize = list.size();
            if (x > totlaSize) x = totlaSize;
            result.addAll(list.subList(x, totlaSize));
        }
        return result;
    }

    /**
     * list兼容获取 从X项开始 取前Y项
     */
    public static <T> ArrayList<T> safeSublistFromXPickY(List<T> list, int x, int y) {
        ArrayList<T> result = new ArrayList<>();
        if (notEmptyList(list)) {
            int totlaSize = list.size();
            int end = x + y;
            if (x > totlaSize) {
                x = totlaSize;
                end = totlaSize;
            } else {
                if (end > totlaSize) end = totlaSize;
            }
            result.addAll(list.subList(x, end));
        }
        return result;
    }

    /**
     * String兼容获取-前x位
     */
    public static String safeSubString(String str, int x) {
        try {
            if (!TextUtils.isEmpty(str) && str.length() >= x) return str.substring(0, x);
            else return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * String兼容获取-从x位开始
     */
    public static String safeSubStringFormX(String str, int fromX) {
        try {
            if (!TextUtils.isEmpty(str) && str.length() >= fromX) return str.substring(fromX);
            else return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * String兼容获取-后x位
     */
    public static String safeSubStringEndX(String str, int endX) {
        try {
            if (!TextUtils.isEmpty(str) && str.length() >= endX) return str.substring(str.length() - endX);
            else return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * list兼容获取数据
     */
    public static <T> T safeGetList(List<T> list, int index) {
        if (notEmptyList(list) && list.size() > index) return list.get(index);
        else return null;
    }

    /**
     * List<String> 变为String，并加上分割符号
     */
    public static String StringList2String(List<String> list, String separate) {
        StringBuilder sb = new StringBuilder();
        if (notEmptyList(list)) {
            for (String s : list) sb.append(s).append(separate);
        }
        String result = sb.toString();
        if (!TextUtils.isEmpty(result)) result = result.substring(0, result.length() - 1);
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
     * 俩long数据相除得double
     */
    public static double twoIntdivideDouble(long a, long b) {
        return ((double) a) / ((double) b);
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

    /**
     * 大于1千单位改为k，保留一位小数。
     * 大于1万单位改为w，保留一位小数。
     * 大于100万单位改为w，不保留小数。
     */
    public static String numDivideqianhewan(int num) {
        String numStr = String.valueOf(num);
        if (num > 1000000) {
            numStr = StrNumUtil.keep0Decimal_divide(num, 10000) + "w";
        } else if (num > 10000) {
            numStr = StrNumUtil.keep1Decimal_divide(num, 10000) + "w";
        } else if (num > 1000) {
            numStr = StrNumUtil.keep1Decimal_divide(num, 1000) + "k";
        }
        return numStr;
    }

    /**
     * 大于1万单位改为w，保留一位小数。
     * 大于100万单位改为w，不保留小数。
     */
    public static String numDividewan(int num) {
        String numStr = String.valueOf(num);
        if (num > 1000000) {
            numStr = keep0Decimal_divide(num, 10000) + "w";
        } else if (num > 10000) {
            numStr = keep1Decimal_divide(num, 10000) + "w";
        }
        return numStr;
    }

    /**
     * 超过100显示99+
     */
    public static String moreThan100(int num) {
        String numStr = String.valueOf(num);
        if (num > 99) numStr = "99+";
        return numStr;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) dp2pxf(context, dpValue);
    }

    public static float dp2pxf(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 格式化存储空间
     */
    public static String getFormatMemorySize(long size) {
        double kiloByte = size / 1024;
        double megaByte = kiloByte / 1024;
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = BigDecimal.valueOf(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
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
     * 分割字符串只要前一个,注意"+"，"?"、"*"要转义 \\+
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
     * 分割字符串只要后一个,注意"+"，"?"、"*"要转义 \\+
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
    public static String keepXDecimal(BigDecimal b, int place) {
        return b.setScale(place, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 两数相除-保留X位小数
     */
    public static String keepXDecimal_divide(BigDecimal b1, BigDecimal b2, int place) {
        try {
            return keepXDecimal(b1.divide(b2), place);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 两数相除-保留X位小数
     */
    public static String keepXDecimal_divide(int i1, int i2, int place) {
        return keepXDecimal_divide(new BigDecimal(i1), new BigDecimal(i2), place);
    }

    /**
     * 两数相除-保留X位小数
     */
    public static String keep2Decimal_divide(int i1, int i2) {
        return keepXDecimal_divide(i1, i2, 2);
    }

    /**
     * 两数相除-保留0位小数
     */
    public static String keep1Decimal_divide(int i1, int i2) {
        return keepXDecimal_divide(i1, i2, 1);
    }

    /**
     * 两数相除-保留0位小数
     */
    public static String keep0Decimal_divide(int i1, int i2) {
        return keepXDecimal_divide(i1, i2, 0);
    }

    /**
     * 保留整数，并每隔3位一个分隔符
     */
    public static String keepZeroDecimalAddDouhao(String res) {
        return new DecimalFormat("###,###").format(Str2Int(res));
    }

    //endregion

    /**
     * <pre>
     * 数字格式化显示
     * 小于万默认显示 大于万以1.7万方式显示最大是9999.9万
     * 大于亿以1.1亿方式显示最大没有限制都是亿单位
     * make by dongxh 2017年12月28日上午10:05:22
     * </pre>
     *
     * @param num 格式化的数字
     * @param b   是否格式化千,为true,并且num大于999就显示999+,小于等于999就正常显示
     */
    public static StringBuffer formatNum(int num, Boolean b) {
        StringBuffer sb = new StringBuffer();
        BigDecimal b0 = new BigDecimal("100");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String unit = "";

        // 以百为单位处理
        if (b) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return sb.append("99+");
            }
            return sb.append(num);
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            formatNumStr = b3.toString();
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            unit = "万";

            formatNumStr = b3.divide(b1).toString();
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            unit = "亿";
            formatNumStr = b3.divide(b2).toString();

        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(unit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(unit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(unit);
                }
            }
        }
        if (sb.length() == 0)
            return sb.append("0");
        return sb;
    }

    //endregion

    //region other more

    public static HashMap<String, String> generateMap(String key, String value) {
        return new HashMap<String, String>() {{
            put(key, value);
        }};
    }

    public static HashMap<String, String> generateMap(String key, String value, String key2, String value2) {
        return new HashMap<String, String>() {{
            put(key, value);
            put(key2, value2);
        }};
    }

    public static HashMap<String, Object> newMap(String key, Object value) {
        return new HashMap<String, Object>() {{
            put(key, value);
        }};
    }

    public static HashMap<String, Object> newMap(String key, Object value, String key2, Object value2) {
        return new HashMap<String, Object>() {{
            put(key, value);
            put(key2, value2);
        }};
    }

    public static HashMap<String, Object> newMap(String key, Object value, String key2, Object value2, String key3, Object value3) {
        return new HashMap<String, Object>() {{
            put(key, value);
            put(key2, value2);
            put(key3, value3);
        }};
    }

    public static HashMap<String, Object> newMap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4) {
        return new HashMap<String, Object>() {{
            put(key, value);
            put(key2, value2);
            put(key3, value3);
            put(key4, value4);
        }};
    }

    public static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static boolean intToBoolean(int i) {
        return i == 1;
    }

    public static String intToBooleanString(int i, String trueStr, String falseStr) {
        return i == 1 ? trueStr : falseStr;
    }

    public static boolean isNeedTestData() {
        return true;
    }

    public static boolean isNeedTestDataRevert() {
        return false;
    }

    public static boolean isNeedTestBoolean(boolean needBoolean) {
        return needBoolean;
    }

    public static boolean noNeedPinonRequest() {
        return true;
    }

    public static boolean showAttention() {
        return false;
    }

    //endregion

    public static class ScreenWidthHeightUtil {
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
}