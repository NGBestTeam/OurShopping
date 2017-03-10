package com.bestteam.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;

public class CommodityDetailsActivity extends AppCompatActivity {

    private Button mAddCar;
    private Button mShopCar;
    private Button mShop;
    private EditText mDetailAddCarNum;
    private EditText mCarNum;
    private TextView mAllMenoy;
    private TextView mTitleTv;
    private TextView mMoneyTv;

    /**
     * 用户是否登录的判断
     */
    private boolean isLogin;

    /**
     * 商品名称和价格
     */
    private String mTitle;
    private String mMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details2);
        initView();
        initPresenter();
    }

    private void initView() {
        mAddCar = (Button) findViewById(R.id.detail_add_car);
        mShopCar = (Button) findViewById(R.id.detail_shop_car);
        mShop = (Button) findViewById(R.id.detail_promptly);
        mTitleTv = (TextView) findViewById(R.id.activity_commodity_title);
        mMoneyTv = (TextView) findViewById(R.id.activity_commodity_money);

        isLogin = SpUtil.getBoolean(this, ConstantValue.IS_LOGIN, false);
    }

    private void initPresenter() {Bundle bundle=getIntent().getExtras();
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

    private void isLogin() {
        if (!isLogin) {
            ToastUtil.show(getApplicationContext(), "请先登录吧！");
            Intent intent = new Intent(CommodityDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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
        detailAddCarTitle = (TextView)  contentView.findViewById(R.id.detail_add_car_title);
        detailAddCarMoney = (TextView)  contentView.findViewById(R.id.detail_add_car_money);
        mAllMenoy = (TextView)  contentView.findViewById(R.id.detail_add_car_allmoney);
        detailAddCarMinus = (TextView)  contentView.findViewById(R.id.detail_add_car_minus);
        mCarNum = (EditText)  contentView.findViewById(R.id.detail_add_car_num);
        detailAddCarAdd = (TextView)  contentView.findViewById(R.id.detail_add_car_add);
        detailAddCarBtn = (Button)  contentView.findViewById(R.id.detail_add_car_btn);
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
                //加入购物

            }
        });
        detailAddCarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(mCarNum.getText().toString());
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
                int num=Integer.parseInt(mCarNum.getText().toString());
                if(num>0){
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
        detailAddCarTitle = (TextView)  contentView.findViewById(R.id.detail_add_car_title);
        detailAddCarMoney = (TextView)  contentView.findViewById(R.id.detail_add_car_money);
        mAllMenoy = (TextView)  contentView.findViewById(R.id.detail_add_car_allmoney);
        detailAddCarMinus = (TextView)  contentView.findViewById(R.id.detail_add_car_minus);
        mCarNum = (EditText)  contentView.findViewById(R.id.detail_add_car_num);
        detailAddCarAdd = (TextView)  contentView.findViewById(R.id.detail_add_car_add);
        detailAddCarBtn = (Button)  contentView.findViewById(R.id.detail_add_car_btn);
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
                int num=Integer.parseInt(mCarNum.getText().toString());
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
                int num=Integer.parseInt(mCarNum.getText().toString());
                if(num>0){
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
