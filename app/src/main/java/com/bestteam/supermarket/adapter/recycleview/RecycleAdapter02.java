package com.bestteam.supermarket.adapter.recycleview;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.HomePurchaseBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by LJWE on 2017/3/7.
 */

public class RecycleAdapter02 extends RecyclerView.Adapter<RecycleAdapter02.ViewHolode>{

    private Context context;
    private List<HomePurchaseBean.HomePurchase> data;

    public RecycleAdapter02(Context context, List<HomePurchaseBean.HomePurchase> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolode onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_rv_item02,parent,false);
        ViewHolode viewHolode = new ViewHolode(view);
        return viewHolode;
    }

    @Override
    public void onBindViewHolder(ViewHolode holder, int position) {
        holder.tv.setText(data.get(position).getDiscountPrice());
        holder.tv2.setText(data.get(position).getPrice());
        holder.tv2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        holder.tv2.getPaint().setAntiAlias(true);
        String url= CommonUrl.replaceImgUrl(data.get(position).getActivityImgPath());

                        Glide.with(context)//  可以接收 Activity  Context Fragment对象
                                .load(url)
//                                .placeholder(R.mipmap.ic_launcher)//加载时显示的资源
//                                .error(R.mipmap.ic_launcher)//加载失败时显示的资源
                                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolode extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv,tv2;
        public ViewHolode(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_rv_item02);
            tv = (TextView) itemView.findViewById(R.id.tv_rv_item02);
            tv2 = (TextView) itemView.findViewById(R.id.tv02_rv_item02);
        }
    }
}
