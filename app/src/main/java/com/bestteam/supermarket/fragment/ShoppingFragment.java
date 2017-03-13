package com.bestteam.supermarket.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.Commodity;
import com.bestteam.supermarket.bean.ShopCartInfo;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 王金瑞 on 17/3/12.
 * 购物车界面 - 作者： 王金瑞
 * 实现了购物车的整体逻辑，与服务器同步的整体逻辑，结算的逻辑
 */

public class ShoppingFragment extends Fragment {

    /**
     * fragment
     */
    private View mShop_View;

    /**
     * 结算的按钮
     */
    private Button mBt_settle;

    /**
     * Recycler的适配器
     */
    private ShopRvAdapter mShopRvAdapter = new ShopRvAdapter();

    /**
     * 当前用户
     */
    private User mUser;

    /**
     * 判断用户是否操作更新
     */
    private boolean isRefresh;

    /**
     * 购物车的数据源
     */
    private List<ShopCartInfo> mShopCartInfos;

    /**
     * 用户更新数据完毕后的状态码
     */
    private static final int USER_UPDATE_DELEAYYED = 0;

    /**
     * 用户点击结算时的进度条
     */
    private ProgressDialog mDialog;

    /**
     * 用户点击结算的标识码
     */
    private boolean isShowAllAccounts;

    /**
     * 购物车集合
     */
    private HashMap<String, Integer> mShopCountMap;

    private Handler mAllHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USER_UPDATE_DELEAYYED:
                    // 结算
                    showAllAccounts();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mShop_View = inflater.inflate(R.layout.fragment, container, false);

        initUI();
        initClick();
        initData();

        return mShop_View;
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        mDialog = new ProgressDialog(getContext());
        mDialog.setTitle("提示：");
        mDialog.setMessage("正在提交，请稍后...");
        mDialog.setCancelable(false);
        mDialog.setIcon(R.mipmap.login_loading);

        RecyclerView rv_shop = (RecyclerView) mShop_View.findViewById(R.id.rv_shop);
        mBt_settle = (Button) mShop_View.findViewById(R.id.bt_settle);

        // 适配器
        rv_shop.setAdapter(mShopRvAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_shop.setLayoutManager(manager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mShopCartInfos = new ArrayList<>();
        // 获得当前用户
        mUser = BmobUser.getCurrentUser(User.class);
        refreshData();
    }

    /**
     * 初始化点击事件
     */
    private void initClick() {
        mBt_settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 刷新数据
                isShowAllAccounts = true;
                refreshData();
            }
        });
    }

    /**
     * 结算
     */
    private void showAllAccounts() {
        if (mShopCartInfos != null && mShopCartInfos.size() > 0) {
            // 购买的件数：
            int shop_count = 0;
            for (ShopCartInfo info : mShopCartInfos) {
                shop_count += info.getCommodityCount();
            }

            double price = mShopRvAdapter.getUserTotalMoney();

            if (price == 0) {
                ToastUtil.show(getContext(), "网络出了点小问题，请稍后再试~...");
            } else {
                // 弹出自定义对话框，并且让购物车清空
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("结算购物车：");
                builder.setMessage("您一共购买了" + shop_count + "件商品\n" + "共消费：" + price + "元！");
                builder.setIcon(R.mipmap.login_loading);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("结算", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.show();

                        mShopCountMap.clear();

                        // 查询多关联表：
                        BmobQuery<Commodity> query = new BmobQuery<>();
                        query.addWhereRelatedTo("commodity", new BmobPointer(mUser));
                        query.findObjects(new FindListener<Commodity>() {
                            @Override
                            public void done(List<Commodity> list, BmobException e) {
                                if (e == null) {
                                    // 查询成功，解除所有关联
                                    BmobRelation relation = new BmobRelation();
                                    for (Commodity commodity : list) {
                                        relation.remove(commodity);
                                    }

                                    // 更新用户
                                    mUser.setCommodity(relation);
                                    mUser.setShopCount(mShopCountMap);
                                    mUser.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            mDialog.dismiss();
                                            if (e == null) {
                                                // 解除成功
                                                ToastUtil.show(getContext(), "购买成功，祝您生活愉快，欢迎下次购买");

                                                // 更新数据源，刷新适配器
                                                mShopCartInfos.clear();
                                                mShopRvAdapter.notifyDataSetChanged();
                                            } else {
                                                ToastUtil.show(getContext(), "关联解除失败：\n" + e.getMessage() + "\n"
                                                        + e.getErrorCode());
                                            }
                                        }
                                    });

                                } else {
                                    mDialog.dismiss();
                                    // 查询失败
                                    ToastUtil.show(getContext(), "服务器出错：\n" + e.getMessage() + "\n"
                                            + e.getErrorCode());
                                }
                            }
                        });
                    }
                });
                builder.show();
            }

        } else {
            ToastUtil.show(getContext(), "您的购物车空空的，不如买点东西吧");
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        if (mShopCartInfos != null && mShopCartInfos.size() > 0) {
            mShopCartInfos.clear();
        }

        mShopCountMap = mUser.getShopCount();

        if (mShopCountMap != null && mShopCountMap.size() > 0) {
            for (Map.Entry<String, Integer> entry : mShopCountMap.entrySet()) {
                String shop_id = entry.getKey();
                int shop_already_count = entry.getValue();
                // 添加至数据源中
                mShopCartInfos.add(new ShopCartInfo(shop_id, shop_already_count, 0));
            }
            isRefresh = true;
            mShopRvAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.show(getContext(), "您的购物车空空的，不如买点东西吧");
        }
    }

    /**
     * 购物车的适配器
     */
    private class ShopRvAdapter extends RecyclerView.Adapter<ShopRvAdapter.MyHolder> {

        /**
         * 用户操作商品数量（移除）更新完成的状态码
         */
        private static final int USER_SUB_SUCCESS = 0;

        /**
         * 用户操作商品数量（添加）更新完成的状态码
         */
        private static final int USER_ADD_SUCCESS = 1;

        /**
         * 用户消费的总金额
         */
        private double totalPrice;

        /**
         * 用来处理用户更新完成后去更新服务器的方法
         */
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Commodity commodity = (Commodity) msg.obj;
                int service = Integer.parseInt(commodity.getLimitcount());

                // 更新服务器商品数量
                switch (msg.what) {
                    case USER_SUB_SUCCESS:
                        commodity.setLimitcount((++service) + "");
                        break;
                    case USER_ADD_SUCCESS:
                        commodity.setLimitcount((--service) + "");
                        break;
                }

                updateServiceCommodity(commodity);
            }
        };

        /**
         * 返回用户消费总金额的方法
         */
        double getUserTotalMoney() {
            return totalPrice;
        }

        /**
         * 更新服务器
         *
         * @param commodity 服务器的商品信息
         */
        private void updateServiceCommodity(Commodity commodity) {
            commodity.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) {
                        ToastUtil.show(getContext(), "服务器更新失败：" + "\n" +
                                e.getMessage() + "\n" + e.getErrorCode());
                    }
                }
            });
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView shop_title, shop_price;
            Button shop_count, subtract, add;

            MyHolder(View itemView) {
                super(itemView);
                shop_title = (TextView) itemView.findViewById(R.id.tv1_shop);
                shop_price = (TextView) itemView.findViewById(R.id.tv2_shop);
                shop_count = (Button) itemView.findViewById(R.id.shop_count);

                subtract = (Button) itemView.findViewById(R.id.subtract);
                add = (Button) itemView.findViewById(R.id.add);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_rv_shop, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {
            holder.shop_count.setText(mShopCartInfos.get(position).getCommodityCount() + "");

            final int temp = position;

            BmobQuery<Commodity> query = new BmobQuery<>();
            query.addWhereEqualTo("objectId", mShopCartInfos.get(position).getCommodityId());
            query.findObjects(new FindListener<Commodity>() {
                @Override
                public void done(final List<Commodity> list, BmobException e) {
                    if (e == null) {
                        // 得到此商品信息
                        String title = list.get(0).getName();
                        String price = list.get(0).getPrice();
                        holder.shop_title.setText(title);
                        holder.shop_price.setText(price);
                        mShopCartInfos.get(temp).setCommodityPrice(Double.parseDouble(price));

                        if (isRefresh) {
                            totalPrice = 0;
                            isRefresh = false;
                        }
                        totalPrice += mShopCartInfos.get(temp).getCommodityCount() * Double.parseDouble(price);

                        if (isShowAllAccounts && temp == mShopCartInfos.size() - 1) {
                            isShowAllAccounts = false;
                            mAllHandler.sendEmptyMessage(USER_UPDATE_DELEAYYED);
                        }


                        /*
                          用户点击减号的逻辑
                         */
                        holder.subtract.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Message message = Message.obtain();
                                message.obj = list.get(0);
                                message.what = USER_SUB_SUCCESS;
                                final int shopCount = Integer.parseInt(holder.shop_count.getText().toString()) - 1;

                                if (shopCount == 0) {
                                    // 购物车集合中去掉此商品元素
                                    mShopCountMap.remove(mShopCartInfos.get(temp).getCommodityId());

                                    // 用户解除与此商品的关联性
                                    BmobRelation relation = new BmobRelation();
                                    relation.remove(list.get(0));

                                    mUser.setShopCount(mShopCountMap);
                                    mUser.setCommodity(relation);

                                    // 更新用户
                                    mUser.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                // 移除成功
                                                ToastUtil.show(getContext(), "该商品已从您的购物车中移除！");
                                                // 更新数据源，刷新适配器
                                                mShopCartInfos.remove(temp);
                                                notifyDataSetChanged();

                                                // 发送消息更新服务器
                                                mHandler.sendMessage(message);
                                            } else {
                                                ToastUtil.show(getContext(), "移除失败：" +
                                                        e.getMessage() + "\n" + e.getErrorCode());
                                            }
                                        }
                                    });

                                } else {
                                    // 用户更新购物车集合中此商品的数量
                                    mShopCountMap.put(mShopCartInfos.get(temp).getCommodityId(), shopCount);

                                    // 更新用户
                                    mUser.setShopCount(mShopCountMap);
                                    mUser.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                // 更新成功
                                                // 控件更新数值
                                                holder.shop_count.setText(shopCount + "");

                                                // 发送消息更新服务器
                                                mHandler.sendMessage(message);
                                            } else {
                                                ToastUtil.show(getContext(), "操作失败：" + "\n" +
                                                        e.getMessage() + "\n" + e.getErrorCode());
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        /*
                          用户点击加号的逻辑
                         */
                        holder.add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Message message = Message.obtain();
                                message.obj = list.get(0);
                                message.what = USER_ADD_SUCCESS;
                                final int shopCount = Integer.parseInt(holder.shop_count.getText().toString()) + 1;

                                if (shopCount > 200) {
                                    ToastUtil.show(getContext(), "对不起，该商品已经没货！");
                                } else {
                                    // 用户更新购物车集合中此商品的数量
                                    mShopCountMap.put(mShopCartInfos.get(temp).getCommodityId(), shopCount);

                                    // 更新用户
                                    mUser.setShopCount(mShopCountMap);
                                    mUser.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                // 更新成功
                                                // 控件更新数值
                                                holder.shop_count.setText(shopCount + "");

                                                // 发送消息更新服务器
                                                mHandler.sendMessage(message);
                                            } else {
                                                ToastUtil.show(getContext(), "操作失败：" + "\n" +
                                                        e.getMessage() + "\n" + e.getErrorCode());
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    } else {
                        ToastUtil.show(getContext(), "网络开小差了哦");
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mShopCartInfos.size();
        }
    }
}
