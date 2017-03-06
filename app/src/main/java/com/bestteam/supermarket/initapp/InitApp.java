package com.bestteam.supermarket.initapp;

import android.app.Application;

import com.bestteam.supermarket.utils.OkHttpManager;

/**
 * Created by WangJinRui on 2017/3/6.
 */

public class InitApp extends Application {
    OkHttpManager mOkHttpManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mOkHttpManager = OkHttpManager.getInstance();
    }
}
