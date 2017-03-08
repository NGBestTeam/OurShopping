package com.bestteam.supermarket.adapter.recycleview;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.base.BaseRecyclerAdapter;
import com.bestteam.supermarket.adapter.base.FullyLinearLayoutManager;
import com.bestteam.supermarket.adapter.base.RecyclerViewHolder;
import com.bestteam.supermarket.adapter.gridview.GvAdapter;
import com.bestteam.supermarket.fragment.HomeDownTab01Fragment;
import com.bestteam.supermarket.fragment.HomeDownTab02Fragment;
import com.bestteam.supermarket.parse.HomePurchaseBean;
import com.bestteam.supermarket.parse.HomeUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 17/3/4.
 * 11111
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private List<String> headImgs; //首页无限轮播图片数据源
    private List<HomeUpBean.Adverts> headData;
    private List<HomePurchaseBean.HomePurchase> dataItem02;
    private List<HomeUpBean.Adverts> datas;
    private BaseRecyclerAdapter.OnItemClickListener mClickListener;
    private List<Fragment> mFragments=new ArrayList<>();

     private List<String> dTitles;
    private String[] mTitles = new String[]{"王金瑞","李俊伟","王宏彦","王金瑞","李俊伟","王宏彦"};


    public HomeRecyclerAdapter(Context context,FragmentManager fragmentManager,List<String> headImgs, List<HomeUpBean.Adverts> headData,
                               List<HomePurchaseBean.HomePurchase> dataItem02,List<HomeUpBean.Adverts> datas
                            ,List<String> dTitles) {
        this.context = context;
        this.fragmentManager=fragmentManager;
        this.headImgs = headImgs;
        this.headData = headData;
        this.dataItem02 = dataItem02;
        this.datas = datas;
        this.dTitles=dTitles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_home_01,parent,false);
                viewHolder=new ViewHolder0(view);
                break;
            case 1:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_item02,parent,false);
                viewHolder=new ViewHolder1(view);

                break;
            case 2:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_iv_rv_item03,parent,false);
                viewHolder=new ViewHolder2(view);
                break;
            case 3:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_down_view,parent,false);
                viewHolder=new ViewHolder3(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType=getItemViewType(position);
        switch (itemViewType){
            case 0:
                ViewHolder0 viewHolder0= (ViewHolder0) holder;
                viewHolder0.vp.setAdapter(new HomePagerAdapter());
                viewHolder0.gv.setAdapter(new GvAdapter(context,headData));
                viewHolder0.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context,"1111"+position,Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 1:
                ViewHolder1 viewHolder1= (ViewHolder1) holder;
              BaseRecyclerAdapter<HomePurchaseBean.HomePurchase> adapter =  new BaseRecyclerAdapter<HomePurchaseBean.HomePurchase>(context,dataItem02){
                    @Override
                    public int getItemLayoutId(int viewType) {
                        return R.layout.item_rv_rv_item02;
                    }

                    @Override
                    public void bindData(RecyclerViewHolder holder, int position, HomePurchaseBean.HomePurchase item) {
                        holder.setText(R.id.tv_rv_item02,item.getDiscountPrice());
                        holder.setText(R.id.tv02_rv_item02,item.getPrice());
                        String url= CommonUrl.replaceImgUrl(item.getActivityImgPath());
                        Glide.with(context)//  可以接收 Activity  Context Fragment对象
                                .load(url)
                                .placeholder(R.mipmap.ic_launcher)//加载时显示的资源
                                .error(R.mipmap.ic_launcher)//加载失败时显示的资源
                                .into(holder.getImageView(R.id.iv_rv_item02));
                    }



                };
                viewHolder1.rv.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int pos) {
                        Toast.makeText(context, "22222"+pos, Toast.LENGTH_SHORT).show();
                    }
                });


                FullyLinearLayoutManager manager = new FullyLinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                viewHolder1.rv.setLayoutManager(manager);
                break;

            case 2:
                ViewHolder2 viewHolder2= (ViewHolder2) holder;
                if (datas.size()>0) {
                    if (position == 7) {
                        ViewGroup.LayoutParams paramas = viewHolder2.iv.getLayoutParams();
                        paramas.height = 140;
                        viewHolder2.iv.setLayoutParams(paramas);
                        viewHolder2.iv.setImageResource(R.mipmap.home_title);
                    } else if (position == 14) {
                        ViewGroup.LayoutParams paramas = viewHolder2.iv.getLayoutParams();
                        paramas.height = 140;
                        viewHolder2.iv.setLayoutParams(paramas);
                        viewHolder2.iv.setImageResource(R.mipmap.market);
                    } else {
                        String url = null;

                        if (position >= 8) {

                            url = CommonUrl.replaceImgUrl(datas.get(position - 3).getImgPath());

                        }
                        if (position >= 15) {
                            url = CommonUrl.replaceImgUrl(datas.get(position - 4).getImgPath());
                        }
                        if (position < 8) {
                            url = CommonUrl.replaceImgUrl(datas.get(position - 2).getImgPath());
                        }

                        Glide.with(context)//  可以接收 Activity  Context Fragment对象
                                .load(url)
                                .into(viewHolder2.iv);
                    }
                }
                break;
            case 3:
                ViewHolder3 viewHolder3= (ViewHolder3) holder;
                mFragments.add(new HomeDownTab01Fragment());
                mFragments.add(new HomeDownTab02Fragment());
                mFragments.add(new HomeDownTab01Fragment());
                mFragments.add(new HomeDownTab02Fragment());
                mFragments.add(new HomeDownTab01Fragment());
                mFragments.add(new HomeDownTab02Fragment());



                viewHolder3.mViewPager.setAdapter(new HomeDownFragmentAdapter(fragmentManager));
                viewHolder3.mViewPager.setOffscreenPageLimit(1);
                viewHolder3.mTabLayout.setupWithViewPager(viewHolder3.mViewPager);
                viewHolder3.mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                for (int i = 0; i < 6; i++) {
                    viewHolder3.mTabLayout.getTabAt(i).setText(mTitles[i]);
                }
                break;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener listener) {
        mClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else if (position==1){
            return 1;
        } else if(position>1 && position<23){
            return 2;
        }else{
            return 3;
        }
    }

    private  class ViewHolder0 extends RecyclerView.ViewHolder
    {
        ViewPager vp;
        GridView gv;
        private ViewHolder0(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.vp_item01);
            gv = (GridView) itemView.findViewById(R.id.gv_item01);
        }
    }

    private  class ViewHolder1 extends RecyclerView.ViewHolder
    {
        RecyclerView rv;
        private ViewHolder1(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv_item02);
        }
    }

    private   class ViewHolder2 extends RecyclerView.ViewHolder
    {
        ImageView iv;
        private ViewHolder2(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_rv_item03);
        }
    }

    private  class ViewHolder3 extends RecyclerView.ViewHolder
    {
        TabLayout mTabLayout;
        ViewPager mViewPager;
        private ViewHolder3(View itemView) {
            super(itemView);
           this.mTabLayout= (TabLayout) itemView.findViewById(R.id.home_down_tablayout);
            this.mViewPager= (ViewPager) itemView.findViewById(R.id.home_down_viewpager);
        }
    }

    private class HomePagerAdapter extends PagerAdapter
    {
        private List<ImageView> mIvs;

        private HomePagerAdapter() {
            initIvs();
        }


        @Override
        public int getCount() {
            return headImgs.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition=position%mIvs.size();
            ImageView imgview=mIvs.get(newPosition);

            ViewGroup parent = (ViewGroup) imgview.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(imgview);


//            container.addView(mImageViewList.get(position));
            return imgview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(mImageViewList.get(position));
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        private void initIvs() {
            mIvs = new ArrayList<>();
            for (int i = 0; i < headImgs.size(); i++) {
                ImageView iv = new ImageView(context);
                String url= CommonUrl.replaceImgUrl(headImgs.get(i));
                Glide.with(context)//  可以接收 Activity  Context Fragment对象
                        .load(url)
                        .placeholder(R.mipmap.ic_launcher)//加载时显示的资源
                        .error(R.mipmap.ic_launcher)//加载失败时显示的资源
                        .into(iv);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mIvs.add(iv);
            }
        }
    }


    //底部视图的ViewPager适配器
    private class HomeDownFragmentAdapter extends FragmentPagerAdapter{

        public HomeDownFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
