package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.ClassRightBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.OkHttpManager;
import com.bestteam.supermarket.view.SpaceItemDecoration;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by WangJinRui on 2017/3/6.
 * 分类界面右侧内容页的Fragment
 */

public class ClassifyContentFragment extends Fragment {

    /**
     * 整个Fragment
     */
    private View mFragment_classify_content;

    /**
     * 此页面的Url
     */
    private String contentUrl = "http://app-client.ffzxnet.com/app-client-web/category/commendCategory.do?params={\"id\":";

    /**
     * RecyclerView的适配器
     */
    private RvContentAdapter mRvContentAdapter = new RvContentAdapter();

    /**
     * Rv的数据源
     */
    private List<ClassRightBean.Items> mItemses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment_classify_content = inflater.inflate(R.layout.fragment_classify_content, container, false);

        initUI();
        initData();

        return mFragment_classify_content;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        /*
      RecyclerView
     */
        RecyclerView rv_classify_content = (RecyclerView) mFragment_classify_content.findViewById(R.id.rv_classify_content);

        // 设置显示方式及适配器
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_classify_content.setLayoutManager(manager);
        rv_classify_content.setAdapter(mRvContentAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String path = getArguments().getString(ConstantValue.CLASSIFY_ID_KEY);
        contentUrl = contentUrl + "\"" + path + "\"" + "}";
        OkHttpManager.getAsync(contentUrl, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                List<ClassRightBean.Items> list = ClassRightBean.getParseClassRightBean(result).getResultData().getItems();
                if (list != null && list.size() > 0) {
                    mItemses.addAll(list);
                    mRvContentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Rv的适配器
     */
    private class RvContentAdapter extends RecyclerView.Adapter<RvContentAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            RecyclerView rv;

            MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_content);
                rv = (RecyclerView) itemView.findViewById(R.id.rv_content);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_rv_classify_content, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mItemses.get(position).getCategoryName());
            GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
            holder.rv.setLayoutManager(manager);
            holder.rv.addItemDecoration(new SpaceItemDecoration(3));
            holder.rv.setAdapter(new RvRvContentAdapter(mItemses.get(position).getItems()));
        }

        @Override
        public int getItemCount() {
            return mItemses.size();
        }


        private class RvRvContentAdapter extends RecyclerView.Adapter<RvRvContentAdapter.MyViewHolder> {
            List<ClassRightBean.DetailedItems> mList;

            RvRvContentAdapter(List<ClassRightBean.DetailedItems> items) {
                mList = items;
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                ImageView iv;
                TextView tv;


                MyViewHolder(View itemView) {
                    super(itemView);
                    iv = (ImageView) itemView.findViewById(R.id.iv_gv_content);
                    tv = (TextView) itemView.findViewById(R.id.tv_gv_content);
                }
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.gv_item_content, parent, false));
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tv.setText(mList.get(position).getAliasName());
                String path = CommonUrl.replaceImgUrl(mList.get(position).getImgPath());
                Glide.with(getContext()).load(path).centerCrop().into(holder.iv);
            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        }
    }
}
