package com.bestteam.supermarket.adapter.recycleview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.gridview.GvAdapter;
import com.bestteam.supermarket.parse.HomeUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 17/3/4.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> headImgs; //首页无限轮播图片数据源
    private List<HomeUpBean.Adverts> headData;



    public HomeRecyclerAdapter(Context context,List<String> headImgs, List<HomeUpBean.Adverts> headData) {
        this.context = context;
        this.headImgs = headImgs;
        this.headData = headData;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_home_01,parent,false);
                viewHolder=new ViewHolder0(view);
                break;
            case 1:
//                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itm_02,parent,false);
//                viewHolder=new ViewHolder1(view);

                break;
            case 2:
//                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itm_03,parent,false);
//                viewHolder=new ViewHolder2(view);
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
                break;
            case 1:
                ViewHolder1 viewHolder1= (ViewHolder1) holder;
                viewHolder1.iv.setImageResource(R.mipmap.ic_launcher);;
                viewHolder1.tv.setText("类型二");
                break;
//            case 2:
//                ViewHolder2 viewHolder2= (ViewHolder2) holder;
//                TextView tv= (TextView) viewHolder2.layout01.getChildAt(0);
//                tv.setText("类型31");
//                break;
            case 2:
                ViewHolder2 viewHolder2= (ViewHolder2) holder;

          //      viewHolder2.iv.setImageResource(imgs[position-6]);


                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else if (position==1){
            return 1;
        }else {
            return 2;
        }
    }

    public  class ViewHolder0 extends RecyclerView.ViewHolder
    {
        ViewPager vp;
        GridView gv;
        public ViewHolder0(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.vp_item01);
            gv = (GridView) itemView.findViewById(R.id.gv_item01);
        }
    }
    public  class ViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView tv;
        ImageView iv;
        public ViewHolder1(View itemView) {
            super(itemView);
        }
    }
    public  class ViewHolder2 extends RecyclerView.ViewHolder
    {
        ImageView iv;
        public ViewHolder2(View itemView) {
            super(itemView);
        }
    }

    private class HomePagerAdapter extends PagerAdapter
    {
        private List<ImageView> mIvs;

        public HomePagerAdapter() {
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






}
