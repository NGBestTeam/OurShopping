package com.bestteam.supermarket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.utils.ToastUtil;

/**
 * 主界面
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ToastUtil.show(this, "我进来了，哈上的封杀的回复");
    }
}
