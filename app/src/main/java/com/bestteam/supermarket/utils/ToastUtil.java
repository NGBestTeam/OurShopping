package com.bestteam.supermarket.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.supermarket.R;

/**
 * Created by WangJinRui on 2017/1/23.
 */
public class ToastUtil {
    public static void show(Context context, String msg){
        Toast toast = new Toast(context);
        View toastCustom = LayoutInflater.from(context).inflate(R.layout.toast_custom, null, false);
        TextView tv = (TextView) toastCustom.findViewById(R.id.toast_custom);
        tv.getBackground().setAlpha(200);
        tv.setText(msg);
        toast.setView(toastCustom);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
