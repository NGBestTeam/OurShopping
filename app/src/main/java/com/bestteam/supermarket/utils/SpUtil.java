package com.bestteam.supermarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by WangJinRui on 2017/1/26.
 */

public class SpUtil {
    private static SharedPreferences sp;
    // 读
    /**
     *
     * @param context   上下文环境
     * @param key       存储结点的名称
     * @param defValue  没有此节点时的默认值
     * @return          读取到的结果
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        // 做成单例
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    // 写
    /**
     *
     * @param context   上下文环境
     * @param key       存储结点的名称
     * @param value     存储节点的值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        // 做成单例
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    // 读
    public static String getString(Context context, String key, String defValue) {
        // 做成单例
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    // 写
    public static void putString(Context context, String key, String value) {
        // 做成单例
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    // 移除某个节点，根据key的唯一性来移除
    public static void remove(Context context, String key) {
        // 做成单例
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).apply();
    }
}
