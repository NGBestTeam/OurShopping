package com.bestteam.supermarket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.adapter.recycleview.ProductRecyclerAdapter;
import com.bestteam.supermarket.parse.Products;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private List<Products.ResultDataBean.ItemsBean> data;
    private ProductRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        LoadData();
    }

    private void LoadData() {
        OkHttpManager.getAsync(CommonUrl.url13, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getApplicationContext(),"网络开小差了。。。",Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                if (result!=null){
                    List<Products.ResultDataBean.ItemsBean> bean=Products.getproductsBean(result).getResultData().getItems();
                    data.addAll(bean);

                    adapter=new ProductRecyclerAdapter(ProductActivity.this,data);
                    adapter.setOnItemClickListener(new ProductRecyclerAdapter.onRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_LONG).show();
                        }
                    });
                    mRv.setAdapter(adapter);

                    LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
                    mRv.setLayoutManager(manager);
                }

            }
        });
    }

    private void initView() {
        mRv= (RecyclerView) findViewById(R.id.rv_product);
        data=new ArrayList<>();

    }
}
