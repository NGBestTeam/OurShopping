package com.bestteam.supermarket.adapter.recycleview;/**
 * Created by Xu on 2017/3/7.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.MySlefDownBean;

import java.util.List;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */
public class MySelfRecyclerAdapter extends RecyclerView.Adapter<MySelfRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<MySlefDownBean> data;

    public MySelfRecyclerAdapter(Context context, List<MySlefDownBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MySelfRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myself_down_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MySelfRecyclerAdapter.ViewHolder holder, final int position) {
        holder.img.setImageResource(data.get(position).getIcon());
        holder.tv.setText(data.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemCickListener.itemClick(holder,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            this.img= (ImageView) itemView.findViewById(R.id.myself_down_recycler_item_img);
            this.tv= (TextView) itemView.findViewById(R.id.myself_down_recycler_item_tv);
        }
    }

    private OnItemCickListener mOnItemCickListener;
    public interface OnItemCickListener{
        void itemClick(ViewHolder viewHolder,int position);
    }
    public void setOnItemClickListener(OnItemCickListener onItemClickListener ){
        mOnItemCickListener=onItemClickListener;
    }

}
