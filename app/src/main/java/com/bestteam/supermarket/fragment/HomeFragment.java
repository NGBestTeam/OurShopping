package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private List<HomeUpBean.Adverts> datas;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }


        view = inflater.inflate(R.layout.fragment_home,container,false);

        mRv = (RecyclerView) view.findViewById(R.id.rv_home);

        initData();
        initAdapter();
        LoadData01();
        loadData02();
        loadData03();

        return view;
    }

    private void loadData03() {

    }

    private void initAdapter() {
        mAdapter = new HomeRecyclerAdapter(getActivity(),headImgs,headData,dataItem02,datas);
        mRv.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),4);
        mRv.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==1||position==0||position==6||position==7||position==13||position==14||position==23) {
                    return 4;
                }else if (position==19||position==20||position==21||position==22)
                {
                    return 1;
                }else {
                    return 2;
                }
            }
        });

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
        datas = new ArrayList<>();
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
                    //第三个条目的数据
                    List<HomeUpBean.Items> items = HomeUpBean.getParseHomeUpBean(result).getResultData().getItems();
                    List<HomeUpBean.Adverts> bean02 = items.get(2).getAdverts();
                    List<HomeUpBean.Adverts> bean03 = items.get(3).getAdverts();
                    List<HomeUpBean.Adverts> bean04 = items.get(4).getAdverts();
                    List<HomeUpBean.Adverts> bean05 = items.get(5).getAdverts();
                    List<HomeUpBean.Adverts> bean06 = items.get(6).getAdverts();
                    List<HomeUpBean.Adverts> bean07 = items.get(7).getAdverts();
                    datas.addAll(bean02);
                    datas.addAll(bean03);
                    datas.addAll(bean04);
                    datas.addAll(bean05);
                    datas.addAll(bean06);
                    datas.addAll(bean07);
                    Log.e("1111", "requestSuccess: "+datas.size() );
                    Log.e("1111", "requestSuccess: "+datas.get(7).getImgPath() );
                    initAdapter();
                    ;                }
            }
        });
    }


}
