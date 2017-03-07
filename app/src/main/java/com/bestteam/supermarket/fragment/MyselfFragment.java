package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.recycleview.MySelfRecyclerAdapter;
import com.bestteam.supermarket.parse.MySlefDownBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 17/3/6.
 */

public class MyselfFragment extends Fragment{

    private View mView;
    //ToolBar
    private Toolbar mToolBar;
    //用户头像 账号
    private ImageView mUserImage;
    private TextView mUserName;
    //四大功能
    private TextView myselfAllFormTv;
    private TextView myselfNoPayTv;
    private TextView myselfNoHaveTv;
    private TextView myselfAfterSaleTv;
    //recyvlerView  界面小功能
    private RecyclerView mRecyclerView;

    private String[] titles={"我的足迹","我的账单","兑换优惠券","我的优惠券","现金红包","收货地址","帮助中心"};
    private int[] icons={R.mipmap.myself_01,R.mipmap.myself_02,R.mipmap.myself_03,R.mipmap.myself_04,
            R.mipmap.myslef_05,R.mipmap.myself_06,R.mipmap.myself_07};
    private MySelfRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_myself,container,false);

        initView();
        setData();
        initControl();

        return mView;
    }

    //Model 数据
    private void setData() {
        List<MySlefDownBean> datas=new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            datas.add(new MySlefDownBean(titles[i],icons[i]));
        }
        mAdapter = new MySelfRecyclerAdapter(getActivity(),datas);

        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    //Control 控制
    private void initControl() {
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"设置搭建中",Toast.LENGTH_SHORT).show();
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_message:
                        Toast.makeText(getActivity(),"消息中心搭建中",Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });

        mAdapter.setOnItemClickListener(new MySelfRecyclerAdapter.OnItemCickListener() {
            @Override
            public void itemClick(MySelfRecyclerAdapter.ViewHolder viewHolder, int position) {
                Toast.makeText(getActivity(), "点击了"+titles[position], Toast.LENGTH_SHORT).show();
            }
        });
        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "个人信息", Toast.LENGTH_SHORT).show();
            }
        });
        myselfAllFormTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "全部订单", Toast.LENGTH_SHORT).show();
            }
        });

        myselfNoPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "待付款", Toast.LENGTH_SHORT).show();
            }
        });
        myselfNoHaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "待收货", Toast.LENGTH_SHORT).show();
            }
        });
        myselfAfterSaleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "退货/售后", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //View 初始化视图
    private void initView() {

        mToolBar = (Toolbar)mView.findViewById( R.id.myself_tool_bar );
        mToolBar.setNavigationIcon(android.R.drawable.ic_dialog_email);
        mToolBar.getNavigationIcon();
        mToolBar.inflateMenu(R.menu.myself_menu);

        mUserImage = (ImageView)mView.findViewById( R.id.myself_user_img );
        mUserName = (TextView)mView.findViewById( R.id.myself_username_tv );

        myselfAllFormTv = (TextView)mView.findViewById( R.id.myself_all_form_tv );
        myselfNoPayTv = (TextView)mView.findViewById( R.id.myself_no_pay_tv );
        myselfNoHaveTv = (TextView)mView.findViewById( R.id.myself_no_have_tv );
        myselfAfterSaleTv = (TextView)mView.findViewById( R.id.myself_after_sale_tv );

        mRecyclerView = (RecyclerView)mView.findViewById( R.id.myself_recycler_view );
    }



}
