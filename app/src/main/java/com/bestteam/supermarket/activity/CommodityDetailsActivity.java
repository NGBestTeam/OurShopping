package com.bestteam.supermarket.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.Commodity;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommodityDetailsActivity extends AppCompatActivity {

    private Button mAddCar;
    private Button mShopCar;
    private Button mShop;
    private EditText mCarNum;
    private TextView mAllMenoy;
    private TextView mTitleTv;
    private TextView mMoneyTv;

    /**
     * 用户是否登录的判断
     */
    private boolean isLogin;

    /**
     * 传过来的商品title，用来查询
     */
    private String name;

    /**
     * 点击加入购物车时的进度条
     */
    private ProgressDialog mAddDialog;

    /**
     * 查询数据完毕的状态码
     */
    private static final int QUERY_FINISH = 0;

    /**
     * 添加购物车以后商品进行更新完成的状态码
     */
    private static final int COMMODITY_UPDATE_FINISH = 1;

    /**
     * 服务器的商品信息
     */
    private Commodity mCommodityService;

    /**
     * 当前用户
     */
    private User mUser;

    /**
     * 商品名称和价格
     */
    private String mTitle;
    private String mMoney;

    /**
     * 用户购买的数量
     */
    private int mBuyCount;

    /**
     * 已经购买数量
     */
    private int mAlreadCount;

    /**
     * 添加时的进度条
     */
    private ProgressDialog mDialog;

    /**
     * 用户的购物车集合
     */
    private HashMap<String, Integer> mShopCountMap;

    /**
     * 处理各种事务的Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case QUERY_FINISH:
                    buyCommodity();
                    break;
                case COMMODITY_UPDATE_FINISH:
                    updateRelation();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details2);
        initView();
        initPresenter();
    }

    /**
     * 加载此页面的商品信息
     */
    private void loadCommodity() {
        BmobQuery<Commodity> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.setLimit(1);
        query.findObjects(new FindListener<Commodity>() {
            @Override
            public void done(List<Commodity> list, BmobException e) {
                if (e == null) {
                    // 查询成功
                    mCommodityService = list.get(0);
                    Log.d("AA", mCommodityService.getName());
                } else {
                    ToastUtil.show(getApplicationContext(), "服务器开小差了哦");
                }
            }
        });
    }

    private void initView() {
        mAddDialog = new ProgressDialog(this);
        mAddDialog.setTitle("提示");
        mAddDialog.setMessage("添加中...");
        mAddDialog.setCancelable(false);
        mAddDialog.setIcon(R.mipmap.login_loading);

        name = getIntent().getStringExtra("titles");
        loadUser();
        mUser = BmobUser.getCurrentUser(User.class);

        Log.d("AA", name);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("提示");
        mDialog.setMessage("请稍后...");
        mDialog.setIcon(R.mipmap.login_loading);
        mDialog.setCancelable(false);

        mAddCar = (Button) findViewById(R.id.detail_add_car);
        mShopCar = (Button) findViewById(R.id.detail_shop_car);
        mShop = (Button) findViewById(R.id.detail_promptly);
        mTitleTv = (TextView) findViewById(R.id.activity_commodity_title);
        mMoneyTv = (TextView) findViewById(R.id.activity_commodity_money);

        isLogin = SpUtil.getBoolean(this, ConstantValue.IS_LOGIN, false);
    }

    /**
     * 刷新用户
     */
    private void loadUser() {
        String userId = SpUtil.getString(getApplicationContext(), ConstantValue.USER_OBJECT_ID, "");
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    mUser = list.get(0);
                    // 将此用户的map集合属性拿到
                    mShopCountMap = mUser.getShopCount();
                    // 加载服务器数据
                    loadCommodity();
                } else {
                    ToastUtil.show(getApplicationContext(), "服务器开小差了哦");
                }
            }
        });
    }

    private void initPresenter() {
        Bundle bundle = getIntent().getExtras();
        mTitle = bundle.getString("titles");
        mMoney = bundle.getString("discountPrice");

        mTitleTv.setText(mTitle);
        mMoneyTv.setText(mMoney);

        //立即购买
        mShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin();
                showProBottomDiaLog();
            }
        });
        //加入购物车
        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin();
                showAddBottomDiaLog();
            }
        });
        //购物车
        mShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin();
            }
        });

    }

    /**
     * 用来判断用户是否登陆
     */
    private void isLogin() {
        if (!isLogin) {
            ToastUtil.show(getApplicationContext(), "请先登录吧！");
            Intent intent = new Intent(CommodityDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 将商品添加至购物车
     */
    private void buyCommodity() {
        mBuyCount = Integer.parseInt(mCarNum.getText().toString());
        int serviceLimitCount = Integer.parseInt(mCommodityService.getLimitcount());

        if (mBuyCount <= 0) {
            ToastUtil.show(getApplicationContext(), "请输入有效值！");
            return;
        }

        if (mCommodityService == null) {
            ToastUtil.show(getApplicationContext(), "服务器开小差了哦，请稍后再试...");
            return;
        }

        if (mBuyCount > serviceLimitCount) {
            ToastUtil.show(getApplicationContext(), "对不起，此商品数量还剩：" + serviceLimitCount + "件！");
            return;
        }

        mAddDialog.show();

        // 将当前商品添加至用户关联的购物车中
        int newLimiteCount = serviceLimitCount - mBuyCount;
        mCommodityService.setLimitcount(newLimiteCount + "");

        // 先将商品信息进行更新后才能添加至购物车。
        mCommodityService.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 商品信息更新成功
                    mHandler.sendEmptyMessage(COMMODITY_UPDATE_FINISH);
                } else {
                    mAddDialog.dismiss();
                    // 更新失败
                    ToastUtil.show(getApplicationContext(), "商品信息更新失败！：" + e.getMessage() + "\n" + e.getErrorCode());
                }
            }
        });

    }

    /**
     * 更新购物车
     */
    private void updateRelation() {
        BmobRelation relation = new BmobRelation();
        relation.add(mCommodityService);

        // 判断此用户有无购物车集合
        if (mShopCountMap == null) {
            Log.d("AA", "他是空的");
            mShopCountMap = new HashMap<>();
        }
        mShopCountMap.put(mCommodityService.getObjectId(), mBuyCount + mAlreadCount);
        mUser.setShopCount(mShopCountMap);
        mUser.setCommodity(relation);
        mUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                mAddDialog.dismiss();
                if (e == null) {
                    // 添加成功！
                    ToastUtil.show(getApplicationContext(), "添加至购物车成功！");
                } else {
                    // 添加失败
                    ToastUtil.show(getApplicationContext(), e.getMessage() + "\n" + e.getErrorCode());
                }
            }
        });
    }


    private void showAddBottomDiaLog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.detail_add_car_popup, null);

        ImageView detailAddCarImg;
        ImageView detailCancel;
        TextView detailAddCarTitle;
        TextView detailAddCarMoney;
        TextView detailAddCarMinus;
        TextView detailAddCarAdd;
        Button detailAddCarBtn;
        detailCancel = (ImageView) contentView.findViewById(R.id.detail_add_car_cancel);
        detailAddCarImg = (ImageView) contentView.findViewById(R.id.detail_add_car_img);
        detailAddCarTitle = (TextView) contentView.findViewById(R.id.detail_add_car_title);
        detailAddCarMoney = (TextView) contentView.findViewById(R.id.detail_add_car_money);
        mAllMenoy = (TextView) contentView.findViewById(R.id.detail_add_car_allmoney);
        detailAddCarMinus = (TextView) contentView.findViewById(R.id.detail_add_car_minus);
        mCarNum = (EditText) contentView.findViewById(R.id.detail_add_car_num);
        detailAddCarAdd = (TextView) contentView.findViewById(R.id.detail_add_car_add);
        detailAddCarBtn = (Button) contentView.findViewById(R.id.detail_add_car_btn);
        mAllMenoy.setText(mMoney);

        detailAddCarMoney.setText(String.valueOf(mMoney));
        bottomSheetDialog.setContentView(contentView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
        detailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        detailAddCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommodityService == null) {
                    ToastUtil.show(getApplicationContext(), "服务器开小差了哦，请稍后再试...");
                    return;
                }
                // 加入购物 mCarNum
                // 先查询购物车中是否有此商品
                BmobQuery<Commodity> query = new BmobQuery<>();
                // commodity是User表中的字段，用来存储用户购买的商品
                User user = new User();
                user.setObjectId(mUser.getObjectId());
                query.addWhereRelatedTo("commodity", new BmobPointer(user));
                query.findObjects(new FindListener<Commodity>() {
                    @Override
                    public void done(List<Commodity> list, BmobException e) {
                        if (list != null && list.size() > 0) {
                            // 找到此商品
                            for (Commodity commodity : list) {
                                if (commodity.getName().equals(name)) {
                                    ToastUtil.show(getApplicationContext(), "您的购物车中已有此商品");
                                    mCommodityService = commodity;

                                    // 拿到此商品的已有购物数量
                                    mAlreadCount = mShopCountMap.get(mCommodityService.getObjectId());
                                }
                            }
                        }
                        mHandler.sendEmptyMessage(QUERY_FINISH);
                    }
                });

            }
        });
        detailAddCarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mCarNum.getText().toString());
                num++;
                mAllMenoy.setText(String.valueOf(num * Float.parseFloat(mMoney)));
                mCarNum.setText(String.valueOf(num));
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mCarNum.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        detailAddCarMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mCarNum.getText().toString());
                if (num > 0) {
                    num--;
                    mAllMenoy.setText(String.valueOf(num * Float.parseFloat(mMoney)));
                    mCarNum.setText(String.valueOf(num));
                }
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mCarNum.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
    }

    private void showProBottomDiaLog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.detail_add_car_popup, null);

        ImageView detailAddCarImg;
        ImageView detailCancel;
        TextView detailAddCarTitle;
        TextView detailAddCarMoney;
        TextView detailAddCarMinus;
        TextView detailAddCarAdd;
        Button detailAddCarBtn;
        detailCancel = (ImageView) contentView.findViewById(R.id.detail_add_car_cancel);
        detailAddCarImg = (ImageView) contentView.findViewById(R.id.detail_add_car_img);
        detailAddCarTitle = (TextView) contentView.findViewById(R.id.detail_add_car_title);
        detailAddCarMoney = (TextView) contentView.findViewById(R.id.detail_add_car_money);
        mAllMenoy = (TextView) contentView.findViewById(R.id.detail_add_car_allmoney);
        detailAddCarMinus = (TextView) contentView.findViewById(R.id.detail_add_car_minus);
        mCarNum = (EditText) contentView.findViewById(R.id.detail_add_car_num);
        detailAddCarAdd = (TextView) contentView.findViewById(R.id.detail_add_car_add);
        detailAddCarBtn = (Button) contentView.findViewById(R.id.detail_add_car_btn);
        mAllMenoy.setText(mMoney);
        detailAddCarMoney.setText(mMoney);
        bottomSheetDialog.setContentView(contentView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
        detailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        detailAddCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //立即·购买
            }
        });
        detailAddCarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mCarNum.getText().toString());
                num++;
                mAllMenoy.setText(String.valueOf(num * Float.parseFloat(mMoney)));
                mCarNum.setText(String.valueOf(num));
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mCarNum.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        detailAddCarMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mCarNum.getText().toString());
                if (num > 0) {
                    num--;
                    mAllMenoy.setText(String.valueOf(num * Float.parseFloat(mMoney)));
                    mCarNum.setText(String.valueOf(num));
                }
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mCarNum.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
    }

}
