package com.bestteam.supermarket.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.base.BaseRecyclerAdapter;
import com.bestteam.supermarket.adapter.base.RecyclerViewHolder;
import com.bestteam.supermarket.adapter.recycleview.HomeRecyclerAdapter;
import com.bestteam.supermarket.parse.HomePurchaseBean;
import com.bestteam.supermarket.parse.HomeUpBean;
import com.bestteam.supermarket.parse.TabLabelBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;
import com.bestteam.supermarket.utils.TitleEvenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

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
    private List<TabLabelBean.Items> dTitles = new ArrayList<>();
    /**
     * titleBar
     */
    private View mTitleView;
    private TitleEvenUtils mTitleBar;
    private GridLayoutManager mManager;
    private RecyclerView mRyTitle;

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


        view = inflater.inflate(R.layout.fragment_home, container, false);

        mRv = (RecyclerView) view.findViewById(R.id.rv_home);
        mRyTitle = (RecyclerView) view.findViewById(R.id.main_view_titles);
        //Title设置 初始化
        mTitleView = view.findViewById(R.id.main_tool_bar);
        mTitleBar = new TitleEvenUtils(mTitleView);

        initData();
        initAdapter();
        LoadData01();
        loadData02();
        loadData03();
        initControl();

        return view;
    }

    private void initControl() {
        mTitleBar.saobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "扫一扫", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), com.zxing.activity.CaptureActivity.class);
                startActivityForResult(intent, 101);
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

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if(recyclerView.getChildCount()>2){
//                    mTitleView.setBackgroundColor(Color.WHITE);
//                }
//                if(recyclerView.getChildCount()<2){
//                    mTitleView.getBackground().setAlpha(0);
//                }

                if (recyclerView.getChildCount() > 6 || recyclerView.getChildCount() == 1) {
                    mTitleView.setBackgroundColor(Color.WHITE);
                }

                if (recyclerView.getChildCount() < 7 && recyclerView.getChildCount() != 1) {
                    mTitleView.getBackground().setAlpha(0);
                }
                Log.e("infoAA", mManager.findFirstVisibleItemPosition() + "个数");
                if (mManager.findFirstVisibleItemPosition() > 19) {
                    mRyTitle.setVisibility(View.VISIBLE);

                    BaseRecyclerAdapter<TabLabelBean.Items> adapter3 = new BaseRecyclerAdapter<TabLabelBean.Items>(getContext(), dTitles) {
                        @Override
                        public int getItemLayoutId(int viewType) {
                            return R.layout.home_down_view_ry_item;
                        }

                        @Override
                        public void bindData(RecyclerViewHolder holder, final int position, TabLabelBean.Items item) {


                            holder.setText(R.id.home_down_view_ry_item_btn, dTitles.get(position).getAliasName());


                            holder.setClickListener(R.id.home_down_view_ry_item_btn, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("home_Down_01", position);
                                    Log.e("infoAA", "--------");
                                    HomeDownTab01Fragment mFragment = new HomeDownTab01Fragment();
                                    mFragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.home_down_view_framelayout, mFragment).commit();
                                }
                            });

                        }
                    };
                    mRyTitle.setAdapter(adapter3);
                    mRyTitle.setAdapter(adapter3);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    mRyTitle.setLayoutManager(layoutManager);
                }
                if (mManager.findFirstVisibleItemPosition() <= 19) {
                    mRyTitle.setVisibility(View.GONE);}
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    /**
     * 扫描二维码
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            String info = data.getExtras().getString("result");
            Toast.makeText(getActivity(), info + "", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData03() {
        //TabLayout 标签 数据源获取
        OkHttpManager.getAsync(CommonUrl.url4, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                dTitles.add(new TabLabelBean().new Items("精选商品", "123", 123, "123"));

                List<TabLabelBean.Items> titles = TabLabelBean.getParseTabLabelBean(result).getResultData().getItems();
                dTitles.addAll(titles);

            }
        });

    }

    private void initAdapter() {
        mAdapter = new HomeRecyclerAdapter(getActivity(), getChildFragmentManager(), headImgs, headData, dataItem02, datas, dTitles);
        mRv.setAdapter(mAdapter);
        mManager = new GridLayoutManager(getActivity(), 4);
        mRv.setLayoutManager(mManager);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 1 || position == 0 || position == 6 || position == 7 || position == 13 || position == 14 || position >= 23) {
                    return 4;
                } else if (position == 19 || position == 20 || position == 21 || position == 22) {
                    return 1;
                } else {
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
                if (result != null) {
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
                if (result != null) {
                    List<HomeUpBean.Items> bean = HomeUpBean.getParseHomeUpBean(result).getResultData().getItems();
                    Log.e("infoAA", HomeUpBean.getParseHomeUpBean(result).getResultData().getItems() + "---chen");
                    //第一个条目的数据
                    for (int i = 0; i < bean.get(0).getAdverts().size(); i++) {
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
                    Log.e("1111", "requestSuccess: " + datas.size());
                    Log.e("1111", "requestSuccess: " + datas.get(7).getImgPath());
                    initAdapter();
                }
            }
        });
    }


}
