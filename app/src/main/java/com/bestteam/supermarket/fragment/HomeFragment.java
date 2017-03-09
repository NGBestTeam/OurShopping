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
import com.bestteam.supermarket.parse.TabAppliancesBean;
import com.bestteam.supermarket.parse.TabFoodBean;
import com.bestteam.supermarket.parse.TabFreshBean;
import com.bestteam.supermarket.parse.TabLabelBean;
import com.bestteam.supermarket.parse.TabSelectionBean;
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
    //RecyclerView数据源 Down
    private List<String> dTitles=new ArrayList<>();
    private List<TabSelectionBean.HomeTabSelection> dTabSelection=new ArrayList<>();
    private List<TabFoodBean.Items> dTabFoodBean=new ArrayList<>();
    private List<TabFreshBean.Items> dTabFreshBean=new ArrayList<>();
    private List<TabAppliancesBean.Items> dTabApplianceBean=new ArrayList<>();

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

//        View view= view.findViewById(R.id.)
        initData();
        initAdapter();
        LoadData01();
        loadData02();
        loadData03();
        initControl();

        return view;
    }

    private void initControl() {

//        viewHolder3.mTabLayout.addTab(viewHolder3.mTabLayout.newTab().setText("王金瑞"));
//        viewHolder3.mTabLayout.addTab(viewHolder3.mTabLayout.newTab().setText("李俊伟"));
//        viewHolder3.mTabLayout.addTab(viewHolder3.mTabLayout.newTab().setText("王宏彦"));
//        viewHolder3.mTabLayout.addTab(viewHolder3.mTabLayout.newTab().setText("王金瑞"));
//        viewHolder3.mTabLayout.addTab(viewHolder3.mTabLayout.newTab().setText("李俊伟"));
    }

    private void loadData03() {
        //TabLayout 标签 数据源获取
        OkHttpManager.getAsync(CommonUrl.url4, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                dTitles.add("今日精选");
                List<TabLabelBean.Items> titles=TabLabelBean.getParseTabLabelBean(result).getResultData().getItems();
                for (int i = 0; i < titles.size(); i++) {
                    dTitles.add(titles.get(i).getAliasName());
                }

            }
        });
//        // 今日精选内容
//        OkHttpManager.getAsync(CommonUrl.url5, new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                List<TabSelectionBean.HomeTabSelection> data=TabSelectionBean.getParseTabSelectionBean(result).getResultData().getItems();
//                dTabSelection.addAll(data);
//            }
//        });
//        //食品酒饮
//        OkHttpManager.getAsync(CommonUrl.url6, new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                List<TabFoodBean.Items> data=TabFoodBean.getParseTabFoodBean(result).getResultData().getItems();
//                dTabFoodBean.addAll(data);
//            }
//        });
//        //生鲜冷藏
//        OkHttpManager.getAsync(CommonUrl.url7, new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                List<TabFreshBean.Items> data=TabFreshBean.getParseTabFreshBean(result).getResultData().getItems();
//                dTabFreshBean.addAll(data);
//            }
//        });
//        //家电汽配
//        OkHttpManager.getAsync(CommonUrl.url8, new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                List<TabAppliancesBean.Items> data= TabAppliancesBean.getParseTabAppliancesBean(result).getResultData().getItems();
//                dTabApplianceBean.addAll(data);
//            }
//        });

    }

    private void initAdapter() {
        mAdapter = new HomeRecyclerAdapter(getActivity(),getFragmentManager(),headImgs,headData,dataItem02,datas,dTitles);
        mRv.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),4);
        mRv.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==1||position==0||position==6||position==7||position==13||position==14||position>=23) {
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
                    for (int i = 0; i <bean.get(0).getAdverts().size() ; i++) {
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
                                    }
            }
        });
    }


}
