package com.bestteam.supermarket.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.ClassLeftBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.OkHttpManager;
import com.bestteam.supermarket.utils.TitleEvenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

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
     * ListView的适配器
     */
    private LvClassifyAdapter mLvClassifyAdapter = new LvClassifyAdapter();

    /**
     * ListView的数据源
     */
    private List<ClassLeftBean.Items> mLvTitles = new ArrayList<>();

    /**
     * 判断是否第一次进入
     */
    private boolean flag;

    /**
     * 判断上一次position是否和当前一样
     */
    private int lastPosition;

    /**
     * Fragment的管理器对象
     */
    private FragmentManager mFragmentManager;
    private View mTitleView;
    private TitleEvenUtils mTitleBar;

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

        mFragmentManager = getFragmentManager();

        initUI();
        initData();
        initControl();
        return mFragment_classify;
    }

    private void initControl() {

        mTitleBar.saobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "扫一扫", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(getActivity(), com.zxing.activity.CaptureActivity.class);
                startActivityForResult(intent,101);
            }
        });
        mTitleBar.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "跳转搜索", Toast.LENGTH_SHORT).show();

            }
        });
        mTitleBar.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "信息搭建中 请改天再来", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        /*
      ListView
     */
        ListView lv_classify = (ListView) mFragment_classify.findViewById(R.id.lv_classify);

        // 设置适配器
        lv_classify.setAdapter(mLvClassifyAdapter);

        // 设置监听
        lv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (lastPosition != position) {
                    lastPosition = position;
                    replaceFragment(position);
                }

                mLvClassifyAdapter.setDefSelect(position);
            }
        });

        //Title设置
        mTitleView = mFragment_classify.findViewById(R.id.main_tool_bar);
        mTitleBar = new TitleEvenUtils(mTitleView);

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
     * 扫描二维码
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK){
            String info=data.getExtras().getString("result");
            Toast.makeText(getActivity(), info+"", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 切换Fragment的方法
     */
    private void replaceFragment(int position) {
        /*
      事务管理器
     */
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        ClassifyContentFragment fragment = new ClassifyContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValue.CLASSIFY_ID_KEY, mLvTitles.get(position).getId());
        fragment.setArguments(bundle);
        transaction.replace(R.id.fl_classify_size, fragment).commit();
    }

    /**
     * ListView的适配器
     */
    private class LvClassifyAdapter extends BaseAdapter {
        private int defItem;

        LvClassifyAdapter() {
            defItem = -1;
        }

        @Override
        public int getCount() {
            return mLvTitles.size();
        }

        void setDefSelect(int position) {
            this.defItem = position;
            notifyDataSetChanged();
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

            if (defItem == position) {
                viewHolder.tv.setSelected(true);
                viewHolder.tv.setTextColor(Color.RED);
            } else {
                viewHolder.tv.setSelected(false);
                viewHolder.tv.setTextColor(Color.BLACK);
            }

            if (!flag && position == 0) {
                flag = true;
                lastPosition = position;
                replaceFragment(position);
                setDefSelect(position);
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
