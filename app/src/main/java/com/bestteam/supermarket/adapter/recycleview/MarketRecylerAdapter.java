package com.bestteam.supermarket.adapter.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.HypermarketDownBean;
import com.bestteam.supermarket.parse.HypermarketUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 17/3/6.
 */

public class MarketRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HypermarketUpBean.Adverts> data;
    private List<HypermarketDownBean.Items> downData;
    private boolean tag=true;
    private onRecyclerViewItemClickListener listener;


    public MarketRecylerAdapter(Context context, List<HypermarketUpBean.Adverts> data,List<HypermarketDownBean.Items> downData) {
        this.context = context;
        this.data = data;
        this.downData=downData;

        data=new ArrayList<>();
        downData=new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_market01,parent,false);
                viewHolder=new ViewHolder0(view,listener);
                break;
            case 1:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_market02,parent,false);
                viewHolder=new ViewHolder1(view,listener);

                break;
    }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType=getItemViewType(position);
        switch (itemViewType){
            case 0:
                HypermarketUpBean.Adverts bean=data.get(position);
                ViewHolder0 viewHolder0= (ViewHolder0) holder;
                String url= CommonUrl.replaceImgUrl(bean.getImgPath());
                Glide.with(context).load(url)
                        .into(viewHolder0.iv);

                break;
            case 1:

                HypermarketDownBean.Items downBean=downData.get(position-4);
                ViewHolder1 viewHolder1= (ViewHolder1) holder;
                String url1= CommonUrl.replaceImgUrl(downBean.getImagePath());
                Glide.with(context).load(url1)
                        .into(viewHolder1.iv);
                viewHolder1.tvLeft.setText(downBean.getActivityTitle());
                viewHolder1.tvRight.setText(downBean.getStatusDesc().substring(18,23));

              if (tag){
                      viewHolder1.tvTitle.setText("精品特卖 每日更新");
                }else {
                  viewHolder1.tvTitle.setText("");
              }
                tag=false;

                if (position==3+downData.size()){
                    viewHolder1.tvBottom.setText("已经到底了");
                }else {
                    viewHolder1.tvBottom.setText("");
                }
                break;

        }

    }

    /**
     * 数据的数量
     */
    @Override
    public int getItemCount() {
        return 4+downData.size();
    }

    /**
     * 返回的数据类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position<4){
            return 0;

        }
        return 1;

    }

    /**
     * 第一种布局
     */
    public  class ViewHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        onRecyclerViewItemClickListener listener;

        public ViewHolder0(View itemView,onRecyclerViewItemClickListener listener) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_market01_item);
            this.listener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener!=null){
                listener.onItemClick(v,getPosition());
            }
        }
    }

    /**
     * 第二种布局
     */
    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv;
        TextView tvLeft,tvRight,tvTitle,tvBottom;
        onRecyclerViewItemClickListener listener=null;

        public ViewHolder1(View itemView,onRecyclerViewItemClickListener listener) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_market02_item);
            tvLeft= (TextView) itemView.findViewById(R.id.tv_market02_left);
            tvRight= (TextView) itemView.findViewById(R.id.tv_market02_right);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_market02_title);
            tvBottom= (TextView) itemView.findViewById(R.id.tv_bottom);

            this.listener=listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onItemClick(v,getPosition());
            }

        }
    }

    public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
        this.listener = listener;

    }



    public interface onRecyclerViewItemClickListener {

        void onItemClick(View v, int  position);
     }



}
