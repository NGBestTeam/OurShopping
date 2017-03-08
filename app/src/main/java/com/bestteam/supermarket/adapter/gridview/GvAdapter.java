package com.bestteam.supermarket.adapter.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.parse.HomeUpBean;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by LJWE on 2017/3/4.
 */

public class GvAdapter extends BaseAdapter {

    private Context context;
    private List<HomeUpBean.Adverts> data;

    public GvAdapter(Context context, List<HomeUpBean.Adverts> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GvViewHolder viewHolder;

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_item01,parent,false);
            viewHolder = new GvViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_gv_item01);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_gv_item01);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (GvViewHolder) convertView.getTag();
        }

        HomeUpBean.Adverts databean= data.get(position);
        viewHolder.tv.setText(databean.getTitle());

        //Gilde加载图片
        String url= CommonUrl.replaceImgUrl(databean.getImgPath());
        Glide.with(context)//  可以接收 Activity  Context Fragment对象
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//加载时显示的资源
                .error(R.mipmap.ic_launcher)//加载失败时显示的资源
                .into(viewHolder.iv);

        return convertView;
    }

    class GvViewHolder{
        ImageView iv;
        TextView tv;
    }
}
