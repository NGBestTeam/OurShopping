package com.bestteam.supermarket.initapp;

import android.app.Application;

import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.OkHttpManager;

import cn.bmob.v3.Bmob;

/**
 * Created by WangJinRui on 2017/3/6.
 */

public class InitApp extends Application {
    OkHttpManager mOkHttpManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化OKHTTP3的管理器
        mOkHttpManager = OkHttpManager.getInstance();

        // 初始化Bmob云
        Bmob.initialize(this, ConstantValue.BMOB_KEY);
    }
}
