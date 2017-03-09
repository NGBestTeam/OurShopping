package com.bestteam.supermarket.adapter.recycleview;/**
 * Created by Xu on 2017/3/9.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.TabSelectionBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */
public class HomeDownRyAdapter extends RecyclerView.Adapter<HomeDownRyAdapter.ViewHolder> {
    private Context mContext;
    private List<TabSelectionBean.HomeTabSelection> data;

    public HomeDownRyAdapter(Context context, List<TabSelectionBean.HomeTabSelection> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.home_down_recyview_item,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String path= CommonUrl.replaceImgUrl(data.get(position).getImgPath());

        holder.mTextView.setText(data.get(position).getTitle1());
        Glide.with(mContext).load(path).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mTextView=(TextView) itemView.findViewById(R.id.home_down_recyview_item_tv);
            this.mImageView= (ImageView) itemView.findViewById(R.id.home_down_recyview_item_img);
        }
    }

}
