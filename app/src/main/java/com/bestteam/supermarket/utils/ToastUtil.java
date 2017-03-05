package com.bestteam.supermarket.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by WangJinRui on 2017/1/23.
 */
public class ToastUtil {
    public static void show(Context context, String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
