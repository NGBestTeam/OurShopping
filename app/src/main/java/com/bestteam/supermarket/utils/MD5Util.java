package com.bestteam.supermarket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WangJinRui on 2017/2/1.
 * 用于对密码等重要信息进行加密
 */

public class MD5Util {

    /**
     *              给指定密码用MD5加密
     * @param psd   需要加密的密码 加盐处理
     * @return      MD5后的处理返回
     */
    public static String encoder(String psd) {
        try {
            // 加盐
            psd = psd + "FeiZhouDaMaiChang:HEHE";
            // 1.指定加密算法
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 2.将需要加密的字符串中转换成byte类型的数组，然后进行随机哈希过程
            byte[] bys = digest.digest(psd.getBytes());
            // 3.循环遍历bys，然后让其生成32位字符串，固定写法
            // 4.作拼接
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bys) {
                int i = b & 0xff;
                // i需要转换成16进制的字符
                String hexString = Integer.toHexString(i);

                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuilder.append(hexString);
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
