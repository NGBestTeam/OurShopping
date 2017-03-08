package com.bestteam.supermarket.adapter.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.Products;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by myself on 17/3/8.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<Products.ResultDataBean.ItemsBean> data;
    private onRecyclerViewItemClickListener itemClickListener;

    public ProductRecyclerAdapter(Context context, List<Products.ResultDataBean.ItemsBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rv_product,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view,itemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products.ResultDataBean.ItemsBean bean=data.get(position);
        String img=bean.getImgPath();
        img=img.replace("size","origin");
        Glide.with(context).load(img).into(holder.iv);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        onRecyclerViewItemClickListener listener;

        public MyViewHolder(View itemView,onRecyclerViewItemClickListener listener) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_product);
            this.listener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v,getPosition());
        }
    }
    public  void setOnItemClickListener(onRecyclerViewItemClickListener itemClickListener){
         this.itemClickListener=itemClickListener;
    }
    public  interface onRecyclerViewItemClickListener{
        void onItemClickListener(View v,int position);
    }

}
