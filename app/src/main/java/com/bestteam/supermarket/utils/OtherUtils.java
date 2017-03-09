package com.bestteam.supermarket.utils;

/**
 * Created by WangJinRui on 2017/3/9.
 * 需要的一些自制工具类
 */

public class OtherUtils {

    /**
     * 创建验证码的工具
     */
    public static String getSMSCodeUtil() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int x = (int) (Math.random() * 10);
            sb.append(x);
        }
        return sb.toString();
    }
}
