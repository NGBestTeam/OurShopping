package com.bestteam.supermarket.fragment;/**
 * Created by Xu on 2017/3/8.
 */

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
import com.bestteam.supermarket.adapter.base.BaseRecyclerAdapter;
import com.bestteam.supermarket.adapter.recycleview.HomeDownRyAdapter;
import com.bestteam.supermarket.parse.TabSelectionBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */
public class HomeDownTab01Fragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private List<TabSelectionBean.HomeTabSelection> data=new ArrayList<>();
    private int num;
    private BaseRecyclerAdapter<TabSelectionBean.HomeTabSelection> mAdapter;
    HomeDownRyAdapter mAdapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.home_down_tab_fragment,container,false);
        initView();

        return mView;
    }

    private void initView(){
        int getPosition=getArguments().getInt("home_Down_01");
        String[] urls={CommonUrl.url5,CommonUrl.url6,CommonUrl.url7,CommonUrl.url8};

        Log.e("infoAA",getPosition+"");
        // 今日精选内容
        OkHttpManager.getAsync(urls[getPosition%4], new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.e("infoAA",result);
                List<TabSelectionBean.HomeTabSelection> data1=TabSelectionBean.getParseTabSelectionBean(result).getResultData().getItems();
                data.addAll(data1);

                mAdapter2.notifyDataSetChanged();

            }
        });
        Log.e("infoAA",data.size()+"-=-=-=-");
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.home_down_tab_fragment_recycler);
//        mAdapter = new BaseRecyclerAdapter<TabSelectionBean.HomeTabSelection>(getActivity(),data) {
//            @Override
//            public int getItemLayoutId(int viewType) {
//                return R.layout.home_down_recyview_item;
//            }
//
//            @Override
//            public void bindData(RecyclerViewHolder holder, final int position, TabSelectionBean.HomeTabSelection item) {
//
//                String path= CommonUrl.replaceImgUrl(data.get(position).getImgPath());
//                Log.e("infoAA",path);
//                Glide.with(getActivity()).load(path).into(holder.getImageView(R.id.home_down_recyview_item_img));
//                holder.setText(R.id.home_down_recyview_item_tv,data.get(position).getTitle1());
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, data.get(position).getTitle1(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        };
        mAdapter2=new HomeDownRyAdapter(getActivity(),data);
        mRecyclerView.setAdapter(mAdapter2);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

}
