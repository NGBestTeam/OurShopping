package com.bestteam.supermarket.fragment;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.recycleview.MarketRecylerAdapter;
import com.bestteam.supermarket.parse.HypermarketDownBean;
import com.bestteam.supermarket.parse.HypermarketUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 大麦场
 *
 */

public class MarketFragment extends Fragment{
    private RecyclerView mRv;
    private MarketRecylerAdapter adapter;
    private List<HypermarketUpBean.Adverts> upData;
    private List<HypermarketDownBean.Items> downData;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_market,container,false);

        init(view);
        LoadData();

        return view;
    }

    /**
     * 初始化数据
     *
     */
    private void init(View view) {
        mRv= (RecyclerView) view.findViewById(R.id.rv_market);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_market);

        swipeRefreshLayout.setColorSchemeColors(Color.CYAN);

        upData=new ArrayList<>();
        downData=new ArrayList<>();

        adapter=new MarketRecylerAdapter(getActivity(),upData,downData);
        mRv.setAdapter(adapter);

        mRv.addItemDecoration(new MyItemDecoration());

    }



    /**
     * 加载数据
     */
    private void LoadData() {
        OkHttpManager.getAsync(CommonUrl.url11, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getContext(),"请求失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {


                if (result!=null){
                    List<HypermarketUpBean.Items> bean=HypermarketUpBean.getParseHypermarketUpBean(result).getResultData().getItems();
                    upData.addAll(bean.get(0).getAdverts());
                    upData.addAll(bean.get(1).getAdverts());

                    adapter.notifyDataSetChanged();


                    GridLayoutManager manager=new GridLayoutManager(getContext(),3);
                    mRv.setLayoutManager(manager);

                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        //返回的position对应的条目所占的size
                        @Override
                        public int getSpanSize(int position) {

                            if (position==0 || position>=4){
                                return 3;
                            } else
                                return 1;
                        }
                    });
                }


            }
        });


        OkHttpManager.getAsync(CommonUrl.url12, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                if (result!=null){
                    List<HypermarketDownBean.Items> bean=HypermarketDownBean.getParseHypermarketDownBean(result).getResultData().getItems();
                    downData.addAll(bean);

                    adapter.notifyDataSetChanged();
                }
            }
        });

        //swipe的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //监听
        mRv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView mRv, MotionEvent e) {
                //处理触摸事件
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView mRv, MotionEvent e) {
                //拦截触摸事件

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                //处理触摸冲突的
            }
        });

    }




    /**
     * 为RecyclerView设置分割线
     */
    class  MyItemDecoration extends  RecyclerView.ItemDecoration{
        /**
         *
         * @param outRect  边界
         * @param view     itemView
         * @param parent   recyclerView
         * @param state    recycler内部数据管理
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //设定底部边距为1px
            outRect.set(0,0,0,1);
        }
    }
}