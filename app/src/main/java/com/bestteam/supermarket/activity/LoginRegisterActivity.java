package com.bestteam.supermarket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/***
 * 登录页中 免费注册 页
 */

public class LoginRegisterActivity extends AppCompatActivity {

    private View mTitle;
    private EditText mPhoneNumberEdt;
    private Button mNextBtn;
    private Button mLoginBtn;
    private Button mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_main);
        initViews();
        initControl();
    }

    private void initViews() {
        mTitle = findViewById( R.id.login_register_title_id );
        mBack= (Button) mTitle.findViewById(R.id.login_register_title_backbtn);
        mPhoneNumberEdt = (EditText)findViewById( R.id.login_register_phonenumber_edt );
        mNextBtn = (Button)findViewById( R.id.login_register_next_btn );
        mLoginBtn = (Button)findViewById( R.id.login_register_login_btn );
    }

    private void initControl() {
        //y页面返回
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //监听输入手机号
        mPhoneNumberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==11){
                    mNextBtn.setBackgroundColor(getResources().getColor(R.color.login_orign));
                    mNextBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<11){
                    mNextBtn.setBackgroundColor(Color.parseColor("#c0c0c0"));
                    mNextBtn.setEnabled(false);
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 下一步的监听逻辑：
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mPhoneNumberEdt.getText().toString();

                if (phone.length()!= 11) {
                    ToastUtil.show(getApplicationContext(), "请输入正确的大陆手机号码");
                    return;
                }

                // 先进行查询此用户（手机号）是否存在
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("mobilePhoneNumber", phone);
                query.setLimit(1);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (list != null && list.size()>0) {
                            ToastUtil.show(getApplicationContext(), "该用户已经存在，如果忘记密码，请找回密码");
                        } else {
                            Intent intent = new Intent(LoginRegisterActivity.this, RegisterInfoActivity.class);
                            intent.putExtra(ConstantValue.USER_REGIST_PHONE, phone);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }

}
