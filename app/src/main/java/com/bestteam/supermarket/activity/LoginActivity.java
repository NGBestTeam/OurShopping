package com.bestteam.supermarket.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.MD5Util;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登陆的主界面
 */

public class LoginActivity extends AppCompatActivity {

    //用户输入  账号    密码
    EditText username, userpwd;
    //用户名填写 清空 密码显影
    Button nameClose, pwdLook;
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
    private boolean isLook = true;

    /**
     * 用户的账号和密码
     */
    private String userNameStr, passWordStr;

    /**
     * 代表账号存在，开启登陆验证的状态码
     */
    private static final int START_LOGIN = 0;

    /**
     * 登陆时出现的进度条
     */
    private ProgressDialog mDialog;

    /**
     * 开启手机登录界面时要求返回的回执码：
     */
    private static final int ENTER_MOBILE_LOGIN = 21;

    /**
     * 用来处理用户登陆事件的Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_LOGIN:
                    startLogin();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        control();
    }

    //控件初始化
    private void initView() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("提示：");
        mDialog.setMessage("正在登录，请稍后...");
        mDialog.setCancelable(false);
        mDialog.setIcon(R.mipmap.login_loading);

        mTitleView = findViewById(R.id.login_title_layout);
        mBack = (Button) findViewById(R.id.login_title_bar_backbtn);

        //用户输入
        username = (EditText) findViewById(R.id.login_edt_username);
        userpwd = (EditText) findViewById(R.id.login_edt_pwd);
        //用户名填写 清空 密码显隐
        nameClose = (Button) findViewById(R.id.login_username_close);
        pwdLook = (Button) findViewById(R.id.login_islook_pwd_btn);
        //登录
        loginCard = (CardView) findViewById(R.id.login_cardview);
        //手机号快捷登录
        mobileLogin = (TextView) findViewById(R.id.login_mobilelogin_tv);
        //忘记密码
        forgetPwd = (TextView) findViewById(R.id.login_forget_tv);
        //免费注册
        registBtn = (Button) findViewById(R.id.login_register_btn);
        //微信登录
        wChatLogin = (Button) findViewById(R.id.login_wchat_btn);
        //qq登录
        qqLogin = (Button) findViewById(R.id.login_qq_btn);

        String userName = SpUtil.getString(this, ConstantValue.USER_NAME, "");
        username.setText(userName);
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
                Intent intent = new Intent(LoginActivity.this, LoginMobileActivity.class);
                startActivityForResult(intent, ENTER_MOBILE_LOGIN);
            }
        });
        //忘记密码
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginPwdLoseActivity.class);
                startActivity(intent);
            }
        });
        //免费注册
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
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
                if (s.length() > 0) {
                    nameClose.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
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
                if (isLook) {
                    pwdLook.setBackgroundResource(R.mipmap.look_pwd);
                    userpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pwdLook.setBackgroundResource(R.mipmap.unlook_pwd);
                    userpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isLook = !isLook;

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
                userNameStr = username.getText().toString();
                passWordStr = userpwd.getText().toString();

                if (userNameStr.length() != 11) {
                    ToastUtil.show(getApplicationContext(), "请输入正确的大陆手机号码");
                    return;
                }

                if (passWordStr.length() < 6) {
                    ToastUtil.show(getApplicationContext(), "密码不能小于6位！");
                    return;
                }

                // 先进行查询此用户（手机号）是否存在
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("mobilePhoneNumber", userNameStr);
                query.setLimit(1);
                mDialog.show();
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            // 证明账号存在，进行登陆验证
                            mHandler.sendEmptyMessage(START_LOGIN);
                        } else {
                            mDialog.dismiss();
                            ToastUtil.show(getApplicationContext(), "手机号用户不存在，请核对后输入");
                        }
                    }
                });
            }
        });
    }

    /**
     * 监听手机登陆页面返回的结果信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ENTER_MOBILE_LOGIN:
                if (resultCode == ConstantValue.MOBILE_LOGIN_OK) {
                    finish();
                }
                break;
        }
    }

    /**
     * 用户开始登陆的方法：
     */
    private void startLogin() {
        User user = new User();
        user.setUsername(userNameStr);
        passWordStr = MD5Util.encoder(passWordStr);
        user.setPassword(passWordStr);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                mDialog.dismiss();
                if (e == null) {
                    SpUtil.putBoolean(getApplicationContext(), ConstantValue.IS_LOGIN, true);
                    SpUtil.putString(getApplicationContext(), ConstantValue.USER_NAME, userNameStr);
                    SpUtil.putString(getApplication(), ConstantValue.USER_OBJECT_ID, user.getObjectId());
                    ToastUtil.show(getApplicationContext(), "登陆成功！");
                    finish();
                } else {
                    ToastUtil.show(getApplicationContext(), "密码输入不正确，请核对后输入");
                }
            }
        });
    }

}
