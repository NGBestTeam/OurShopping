package com.bestteam.supermarket.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class CommodityDetailsActivity extends AppCompatActivity {

    private Button mAddCar;
    private Button mShopCar;
    private Button mShop;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private EditText mDetailAddCarNum;
    private EditText mCarNum;
    private TextView mAllMenoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details2);
        initView();
        initPresenter();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        mAddCar = (Button) findViewById(R.id.detail_add_car);
        mShopCar = (Button) findViewById(R.id.detail_shop_car);
        mShop = (Button) findViewById(R.id.detail_promptly);
    }

    private void initPresenter() {
        //立即购买
        mShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //加入购物车
        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDiaLog();
            }
        });
        //购物车
        mShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void showBottomDiaLog() {
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
                mAllMenoy.setText(String.valueOf(num*12.60));
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
                    mAllMenoy.setText(String.valueOf(num*12.60));
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CommodityDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}
