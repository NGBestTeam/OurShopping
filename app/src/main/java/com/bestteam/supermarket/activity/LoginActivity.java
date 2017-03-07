package com.bestteam.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bestteam.supermarket.R;

/**
 *
 */

public class LoginActivity extends AppCompatActivity {

    //用户输入  账号    密码
    EditText username,userpwd;
    //用户名填写 清空 密码显影
    Button nameClose,pwdLook;
    //登录
    CardView loginCard;
    //手机号快捷登录
    TextView mobileLogin;
    //忘记密码
    TextView forgetPwd;
    //免费注册
    Button registBtn;
    //微信登录
    Button wChatLogin;
    //qq登录
    Button qqLogin;
    //Title
    private View mTitleView;
    private Button mBack;
    //判断look Pwd
    private boolean isLook=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        control();
    }

    //控件初始化
    private void initView() {
        mTitleView = (View) findViewById(R.id.login_title_layout);
        mBack = (Button) findViewById(R.id.login_title_bar_backbtn);

        //用户输入
        username= (EditText) findViewById(R.id.login_edt_username);
        userpwd= (EditText) findViewById(R.id.login_edt_pwd);
        //用户名填写 清空 密码显隐
        nameClose= (Button) findViewById(R.id.login_username_close);
        pwdLook= (Button) findViewById(R.id.login_islook_pwd_btn);
        //登录
        loginCard= (CardView) findViewById(R.id.login_cardview);
        //手机号快捷登录
        mobileLogin= (TextView) findViewById(R.id.login_mobilelogin_tv);
        //忘记密码
        forgetPwd= (TextView) findViewById(R.id.login_forget_tv);
        //免费注册
        registBtn= (Button) findViewById(R.id.login_register_btn);
        //微信登录
        wChatLogin= (Button) findViewById(R.id.login_wchat_btn);
        //qq登录
        qqLogin= (Button) findViewById(R.id.login_qq_btn);

    }

    //控件控制
    private void control() {
        //标题返回键
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //手机登录
        mobileLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginMobileActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginPwdLoseActivity.class);
                startActivity(intent);
            }
        });
        //免费注册
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginRegisterActivity.class);
                startActivity(intent);
            }
        });

        //用户名输入监听
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    nameClose.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    nameClose.setVisibility(View.GONE);
                }
            }
        });
        //username的清空
        nameClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
            }
        });

        //userPwd显示隐藏
        pwdLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLook){
                    pwdLook.setBackgroundResource(R.mipmap.look_pwd);
                    userpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    pwdLook.setBackgroundResource(R.mipmap.unlook_pwd);
                    userpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isLook=!isLook;

                //切换后将EditText光标置于末尾
                CharSequence charSequence = userpwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });

        loginCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName=username.getText().toString();
                String uPwd=userpwd.getText().toString();

                if(!(TextUtils.isEmpty(uName) && TextUtils.isEmpty(uPwd))){
                    //需要自定义Toast
                }else{
                    //进行请求验证

                }

            }
        });


    }


}
