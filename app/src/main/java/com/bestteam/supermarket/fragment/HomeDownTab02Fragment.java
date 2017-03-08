package com.bestteam.supermarket.fragment;/**
 * Created by Xu on 2017/3/8.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.base.BaseRecyclerAdapter;
import com.bestteam.supermarket.adapter.base.RecyclerViewHolder;
import com.bestteam.supermarket.parse.TabFoodBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;
import com.bumptech.glide.Glide;

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
public class HomeDownTab02Fragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private List<TabFoodBean.Items> data=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.home_down_tab2_fragment,container,false);
        initView();

        return mView;
    }

    private void initView(){


        mRecyclerView = (RecyclerView) mView.findViewById(R.id.home_down_tab_fragment_recycler2);
        BaseRecyclerAdapter<TabFoodBean.Items> adapter=new BaseRecyclerAdapter<TabFoodBean.Items>(getActivity(),data) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.home_down_recyview_item;
            }

            @Override
            public void bindData(final RecyclerViewHolder holder, final int position,TabFoodBean.Items item) {
                Log.e("infoAA",data.size()+"---");
                String path= CommonUrl.replaceImgUrl(data.get(position).getImgPath());
                Glide.with(getActivity()).load(path).into(holder.getImageView(R.id.home_down_recyview_item_img));
                holder.setText(R.id.home_down_recyview_item_tv,data.get(position).getTitle1());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, ""+data.get(position).getTitle1(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //食品酒饮
        OkHttpManager.getAsync(CommonUrl.url6, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                data.clear();
                List<TabFoodBean.Items> data1=TabFoodBean.getParseTabFoodBean(result).getResultData().getItems();
                data.addAll(data1);
                Log.e("infoAA","进入");
            }
        });
    }
}
