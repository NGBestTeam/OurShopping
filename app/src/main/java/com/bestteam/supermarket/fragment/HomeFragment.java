package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.recycleview.HomeRecyclerAdapter;
import com.bestteam.supermarket.parse.HomePurchaseBean;
import com.bestteam.supermarket.parse.HomeUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by LJWE on 2017/3/4.
 */

public class HomeFragment extends Fragment {

    private RecyclerView mRv;
    private HomeRecyclerAdapter mAdapter;
    //RecycleView数据源
    private List<HomeUpBean.Adverts> headData;
    private List<String> headImgs;
    private List<HomePurchaseBean.HomePurchase> dataItem02;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mRv = (RecyclerView) view.findViewById(R.id.rv_home);

        initData();
        initAdapter();
        LoadData01();
        loadData02();

        return view;
    }

    private void initAdapter() {
        mAdapter = new HomeRecyclerAdapter(getActivity(),headImgs,headData,dataItem02);
        mRv.setAdapter(mAdapter);
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRv.setLayoutManager(lManager);

    }


    private void loadData02() {
        OkHttpManager.getAsync(CommonUrl.url2, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                //第二个条目的数据
                if (result!=null){
                    List<HomePurchaseBean.HomePurchase> bean02 = HomePurchaseBean.getParseHomePurchaseBean(result)
                            .getResultData().getItems();
                    dataItem02.addAll(bean02);
                    initAdapter();
                }


            }
        });
    }

    private void initData() {
        headData = new ArrayList<>();
        headImgs = new ArrayList<>();
        dataItem02 = new ArrayList<>();
    }

    private void LoadData01() {

        OkHttpManager.getAsync(CommonUrl.url1, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                if (result!=null){
                    List<HomeUpBean.Items> bean=HomeUpBean.getParseHomeUpBean(result).getResultData().getItems();
                    //第一个条目的数据
                    for (int i = 0; i < 3; i++) {
                        headImgs.add(bean.get(0).getAdverts().get(i).getImgPath());
                    }
                    headData.addAll(bean.get(1).getAdverts());
                    initAdapter();
                    //第二个条目的数据
                }
            }
        });
    }


}
