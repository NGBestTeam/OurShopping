package com.bestteam.supermarket.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bestteam.supermarket.R;

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


    }

    private void initViews() {
        mTitle = (View)findViewById( R.id.login_register_title_id );
        mBack= (Button) mTitle.findViewById(R.id.login_register_title_backbtn);
        mPhoneNumberEdt = (EditText)findViewById( R.id.login_register_phonenumber_edt );
        mNextBtn = (Button)findViewById( R.id.login_register_next_btn );
        mLoginBtn = (Button)findViewById( R.id.login_register_login_btn );

    }





}
