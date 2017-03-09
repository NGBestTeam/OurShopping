package com.bestteam.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.fragment.ClassifyFragment;
import com.bestteam.supermarket.fragment.HomeFragment;
import com.bestteam.supermarket.fragment.MarketFragment;
import com.bestteam.supermarket.fragment.MyselfFragment;
import com.bestteam.supermarket.fragment.ShoppingFragment;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.Calendar;

/**
 * 主界面
 */

public class HomeActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class mFragments[] = {HomeFragment.class, ClassifyFragment.class, MarketFragment.class, ShoppingFragment.class, MyselfFragment.class};
    private String mTitles[] = {"首页", "分类", "大卖场", "购物车", "我的"};
    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_type_btn, R.drawable.tab_market_btn, R.drawable.tab_myself_btn, R.drawable.tab_shopping_btn};

    /**
     * 用来判断第一次点击Back的时间
     */
    private long firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_home);

        //得到fragment的个数
        for (int i = 0; i < mFragments.length; i++) {
            TabHost.TabSpec tabbspec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabItemView(i));
            //将tab按钮添加进tab选项卡中
            mTabHost.addTab(tabbspec, mFragments[i], null);
        }

        mTabHost.getTabWidget().getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLogin(3);
            }
        });

        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLogin(4);
            }
        });
    }

    /**
     * 进入登陆界面之前的判断
     * @param position  用户想要切换的Fragment页卡
     */
    private void enterLogin(int position) {
        // 先判断本地的当前登陆状态
        Boolean isLogin = SpUtil.getBoolean(this, ConstantValue.IS_LOGIN, false);

        if (isLogin) {
            // 获取上一次的时间和此次作比较
            int diffDays = compareTime();
            if (diffDays > 7) {
                // 登录状态变为否
                SpUtil.putBoolean(this, ConstantValue.IS_LOGIN, false);

                // 将已保存的密码清空
                SpUtil.putString(this, ConstantValue.USER_PASSWORD, "");

                // 进入登陆界面
                startEnterLogin();
            } else {
                // 跳转至相应的Fragment
                mTabHost.setCurrentTab(position);
            }
        } else {
            // 进入登陆界面
            startEnterLogin();
        }

    }

    /**
     * 开启进入登陆界面
     */
    private void startEnterLogin() {
        ToastUtil.show(this, "请先登录哦！");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 比较上次登入时间和此次登入时间的时间差
     */
    private int compareTime() {
        String lastTime = SpUtil.getString(this, ConstantValue.ENTER_APP_TIME, "");
        Calendar lastC = Calendar.getInstance();
        String[] lt = lastTime.split("/");
        lastC.set(Integer.parseInt(lt[0]), Integer.parseInt(lt[1]), Integer.parseInt(lt[2]));
        Calendar currC = Calendar.getInstance();

        // 将此次登录时间作为上一次登录时间
        int year = currC.get(Calendar.YEAR);
        int month = currC.get(Calendar.MONTH);
        int day = currC.get(Calendar.DAY_OF_MONTH);
        String time = year + "/" + month + "/" + day;
        SpUtil.putString(this, ConstantValue.ENTER_APP_TIME, time);

        return (int)((currC.getTimeInMillis() - lastC.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取tab的子视图控件
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_item);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tv_tab_item);
        textView.setText(mTitles[index]);

        return view;
    }

    /**
     * 监听推出事件
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long currentTime = System.currentTimeMillis();

                if (currentTime - firstTime > 2000) {
                    ToastUtil.show(this, "再按一次退出程序");
                    firstTime = currentTime;
                } else {
                    finish();
                }

                break;
        }

        return true;
    }
}
