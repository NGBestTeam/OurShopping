package com.bestteam.supermarket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bestteam.supermarket.R;

/**
 * 登录页中 手机快捷登录
 *
 */

public class LoginMobileActivity extends AppCompatActivity {

    //标题栏
    private View loginMobileTitleId;
    //输入手机号
    private EditText loginMobilePhonenumberEdt;
    //获取验证码
    private Button loginMobileGetverifyBtn;
    //输入验证码
    private EditText loginMobileVerifyEdt;
    //验证登录
    private Button loginMobileLoginbtn;
    //返回键
    private Button mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);
        initViews() ;
        initControl();
    }

    private void initViews() {
        loginMobileTitleId = (View)findViewById( R.id.login_mobile_title_id );
        mBack= (Button) loginMobileTitleId.findViewById(R.id.login_mobile_title_backbtn);
        loginMobilePhonenumberEdt = (EditText)findViewById( R.id.login_mobile_phonenumber_edt );
        loginMobileGetverifyBtn = (Button)findViewById( R.id.login_mobile_getverify_btn );
        loginMobileVerifyEdt = (EditText)findViewById( R.id.login_mobile_verify_edt );
        loginMobileLoginbtn = (Button)findViewById( R.id.login_mobile_loginbtn );

    }

    private void initControl() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
