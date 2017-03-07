package com.bestteam.supermarket.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.fragment.ClassifyFragment;
import com.bestteam.supermarket.fragment.HomeFragment;
import com.bestteam.supermarket.fragment.MarketFragment;
import com.bestteam.supermarket.fragment.MyselfFragment;
import com.bestteam.supermarket.fragment.ShoppingFragment;
import com.bestteam.supermarket.utils.ToastUtil;

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
        setContentView(R.layout.activity_home);

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
                // 进行本地登录状态判断
            }
        });

        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进行本地登录状态判断
            }
        });
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
