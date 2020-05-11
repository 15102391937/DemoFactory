package com.cgy.utils;

/**
 * Created by ChenGY on 2019/11/12 0012.
 */
public class TestDataUtil {

    //region getBean method



    //endregion

    //region random method

    /**
     * 获取随机Int的字符串
     */
    public static String getRandomIntStr(int rang) {
        return (int) (Math.random() * rang) + "";
    }

    /**
     * 获取随机float的字符串
     */
    public static String getRandomFloatStr(int rang) {
        return StrNumUtil.keepTwoDecimal(Math.random() * rang);
    }

    /**
     * 获取随机Int值
     */
    public static int getRandomInt(int rang) {
        return (int) (Math.random() * rang);
    }

    /**
     * 获取随机boolean值
     */
    public static boolean getRandomBoolean() {
        return ((int) (Math.random() * 1000)) % 2 == 0;
    }

    /**
     * 获取随机Str值
     */
    public static String getRandomStr(String str1, String str2) {
        return getRandomBoolean() ? str1 : str2;
    }

    /**
     * 获取随机Str值1或者2
     */
    public static String getRandomStr12() {
        return getRandomBoolean() ? "1" : "2";
    }

    /**
     * 获取随机Str值
     */
    public static String getRandomStr(String[] strs) {
        if (strs != null && strs.length > 0) {
            int i = getRandomInt(1000) % strs.length;
            return strs[i];
        } else {
            return "";
        }
    }

    //endregion

    //region constant

    private static final int LIST_TOTAL = 30;
    private static final boolean IS_NEED_TEST_DATA = true;
    private static final boolean IS_NEED_WALLET_TEST_DATA = false;

    public static boolean isNeedTestData() {
        return BuildConfig.DEBUG && IS_NEED_TEST_DATA;
    }

    public static boolean isNeedWalletTestData() {
        return BuildConfig.DEBUG && IS_NEED_WALLET_TEST_DATA;
    }

    //endregion

}
