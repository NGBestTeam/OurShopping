package com.bestteam.supermarket.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.ClassLeftBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by WangJinRui on 2017/3/6.
 * 分类的Fragment
 */

public class ClassifyFragment extends Fragment {

    /**
     * 整个Fragment
     */
    private View mFragment_classify;

    /**
     * ListView
     */
    private ListView mLv_classify;

    /**
     * ListView的适配器
     */
    private LvClassifyAdapter mLvClassifyAdapter = new LvClassifyAdapter();

    /**
     * ListView的数据源
     */
    private List<ClassLeftBean.Items> mLvTitles = new ArrayList<>();

    /**
     * 判断ListView的Item前一次被选中的下标
     */
    private int flag = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 判断Fragment有无被回收问题
        if (mFragment_classify != null) {
            ViewGroup parent = (ViewGroup) mFragment_classify.getParent();
            if (parent != null) {
                parent.removeView(mFragment_classify);
            }
            return mFragment_classify;
        }

        mFragment_classify = inflater.inflate(R.layout.fragment_classify, container, false);

        initUI();
        initData();

        return mFragment_classify;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        OkHttpManager.getAsync(CommonUrl.url9, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                List<ClassLeftBean.Items> itemses = ClassLeftBean.getParseClassLeftBean(result).getResultData().getItems();
                if (itemses != null && itemses.size() > 0) {
                    mLvTitles.addAll(itemses);
                    mLvClassifyAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mLv_classify = (ListView) mFragment_classify.findViewById(R.id.lv_classify);

        // 设置适配器
        mLv_classify.setAdapter(mLvClassifyAdapter);

        // 设置监听
        mLv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv;
                if (flag == -1) {
                    flag = position;
                    View view1 = mLv_classify.getChildAt(flag);
                    tv = (TextView) view1.findViewById(R.id.tv_lv_classify);
                    tv.setTextColor(Color.RED);
                    tv.setSelected(true);
                } else {
                    if (flag != position) {
                        View view1 = mLv_classify.getChildAt(flag);
                        tv = (TextView) view1.findViewById(R.id.tv_lv_classify);
                        tv.setTextColor(Color.BLACK);
                        tv.setSelected(false);
                        flag = position;
                        tv = (TextView) view.findViewById(R.id.tv_lv_classify);
                        tv.setTextColor(Color.RED);
                        tv.setSelected(true);
                    }
                }
            }
        });
    }

    /**
     * ListView的适配器
     */
    private class LvClassifyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mLvTitles.size();
        }

        @Override
        public Object getItem(int position) {
            return mLvTitles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lv_classify, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(mLvTitles.get(position).getAliasName());

            return convertView;
        }

        private class ViewHolder {
            TextView tv;

            ViewHolder(View view) {
                tv = (TextView) view.findViewById(R.id.tv_lv_classify);
            }

        }
    }
}
