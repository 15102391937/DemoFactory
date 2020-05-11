package com.cgy.utils;

import android.content.Context;

/**
 * Created by ChenGY on 2019/10/29 0029.
 */
public class CodeTransStrUtil {

    /**
     * 好友请求来源
     */
    public static String friendRequest(String code) {
        String result = "";
        switch (StrNumUtil.getEmptyStr(code)) {
            case "-1":
                result = "来自电话簿";
                break;
            case "1":
                result = "来自临时聊天";
                break;
            case "2":
                result = "来自个人资料卡";
                break;
            case "3":
                result = "来自广场";
                break;
            case "4":
                result = "来自扫二维码";
                break;
            case "5":
                result = "来自扫脸";
                break;
            case "6":
                result = "来自群搜索";
                break;
            case "7":
                result = "来自群推荐";
                break;
            case "8":
                result = "来自群二维码";
                break;
        }
        return result;
    }
}
