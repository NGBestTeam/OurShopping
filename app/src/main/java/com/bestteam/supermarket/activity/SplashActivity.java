package com.bestteam.supermarket.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    /**
     * 首次进入页面的ViewPager
     */
    private ViewPager mVp_spactivity;

    /**
     * viewpager的指示器
     */
    private LinearLayout mLinear_spactivity;

    /**
     * viewpager的图片数据
     */
    private List<ImageView> mBottomImgs = new ArrayList<>();

    /**
     * 指示器的数据
     */
    // private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次进入本程序
        boolean first_enter_app = SpUtil.getBoolean(this, ConstantValue.FIRST_ENTER_APP, false);
        if (first_enter_app) {
            SpUtil.putBoolean(this, ConstantValue.FIRST_ENTER_APP, true);
            setContentView(R.layout.activity_splash_first_enter);

            initFirstEnterUI();
        } else {
            setContentView(R.layout.activity_splash);
        }

    }

    /**
     * 首次进入页面后初始化UI
     */
    private void initFirstEnterUI() {
        mVp_spactivity = (ViewPager) findViewById(R.id.vp_spactivity);
        mLinear_spactivity = (LinearLayout) findViewById(R.id.linear_spactivity);

        // 初始化数据
    }
}
