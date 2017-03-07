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

/**
 * 登录页 忘记密码
 */

public class LoginPwdLoseActivity extends AppCompatActivity {


    private EditText mPhoneNumber;
    private Button mNext;
    private View mTitleView;
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd_lose);
        initView();
        initControl();

    }

    private void initControl() {

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s==null){
                    mNext.setBackgroundColor(Color.parseColor("#c0c0c0"));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null){
                    mNext.setBackgroundDrawable(getResources().getDrawable(R.color.login_orign));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<=3){
                    mNext.setBackgroundColor(Color.parseColor("#c0c0c0"));
                }
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {

        mTitleView = (View) findViewById(R.id.login_find_pwd_title_id);
        mBack = (Button) findViewById(R.id.login_find_pwd_title_backbtn);
        mNext = (Button) findViewById(R.id.login_pwd_lose_next_btn);
        mPhoneNumber = (EditText) findViewById(R.id.login_find_pwd_edt);
    }


}
