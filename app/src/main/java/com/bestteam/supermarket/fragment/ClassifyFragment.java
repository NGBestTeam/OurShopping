package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bestteam.supermarket.R;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> mLvTitles = new ArrayList<>();

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

        return mFragment_classify;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mLv_classify = (ListView) mFragment_classify.findViewById(R.id.lv_classify);

        // 设置适配器
        mLv_classify.setAdapter(mLvClassifyAdapter);
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
            viewHolder.tv.setText(mLvTitles.get(position));

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
