package com.bestteam.supermarket.adapter.recycleview;/**
 * Created by Xu on 2017/3/10.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.fragment.HomeDownTab01Fragment;
import com.bestteam.supermarket.parse.TabLabelBean;

import java.util.List;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */
public class HomeDownRytestAdapter extends RecyclerView.Adapter<HomeDownRytestAdapter.ViewHolder> {
    private Context mContext;
    private List<TabLabelBean.Items> data;
    private FragmentManager fragmentManager;
    private FrameLayout frame;
    private int lastPostion=0;

    public HomeDownRytestAdapter(Context context, List<TabLabelBean.Items> data, FrameLayout frame,FragmentManager fragmentManager) {
        mContext = context;
        this.data = data;
        this.frame=frame;
        this.fragmentManager=fragmentManager;

    }

    @Override
    public HomeDownRytestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_down_view_ry_item,null);
         ViewHolder viewholder=new HomeDownRytestAdapter.ViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final HomeDownRytestAdapter.ViewHolder holder, final int position) {
          holder.btn.setText(data.get(position).getAliasName());
//        if(position!=0){
//            holder.view.setBackgroundColor(Color.WHITE);
//        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(holder,position);
            }
        });

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle=new Bundle();
                    bundle.putInt("home_Down_01",position);
                    Log.e("infoAA","--------");
                    HomeDownTab01Fragment mFragment=new HomeDownTab01Fragment();
                    mFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.home_down_view_framelayout, mFragment).commit();
                }
            });
//        if(position==0){
//
//            holder.btn.performClick();
//        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        Button btn;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);

            this.btn= (Button) itemView.findViewById(R.id.home_down_view_ry_item_btn);
            this.view=itemView.findViewById(R.id.home_down_view_ry_item_view);
        }

    }
    private OnViewClickListener mOnClickListener;
    public interface OnViewClickListener{
        void onClick(HomeDownRytestAdapter.ViewHolder view,int pos );
    }

    public void setOnViewClickListener(OnViewClickListener mOnClickListener){
        this.mOnClickListener=mOnClickListener;
    }

}
