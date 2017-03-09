package com.bestteam.supermarket.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.OtherUtils;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 登录页中 手机快捷登录
 */

public class LoginMobileActivity extends AppCompatActivity {

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

    /**
     * 用户输入的手机号码
     */
    private String userPhone;

    /**
     * 账号未注册的状态码
     */
    private static final int USER_NO_REGIST = 101;

    /**
     * 发送验证码的时间
     */
    private long sendTime;

    /**
     * 获取验证码的点击事件的时间计算
     */
    private static final int VERIFYBTN_CLICK = 103;

    /**
     * 处理验证码的Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USER_NO_REGIST:
                    // 使获取验证码的按钮倒计时2分钟后变成可点击
                    mHandler.sendEmptyMessageDelayed(VERIFYBTN_CLICK, 2 * 60 * 1000);

                    getSMSCode();
                    break;
                case VERIFYBTN_CLICK:
                    loginMobileGetverifyBtn.setEnabled(true);
                    loginMobileGetverifyBtn.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);
        initViews();
        initControl();
    }

    private void initViews() {
        View loginMobileTitleId = findViewById(R.id.login_mobile_title_id);
        mBack = (Button) loginMobileTitleId.findViewById(R.id.login_mobile_title_backbtn);
        loginMobilePhonenumberEdt = (EditText) findViewById(R.id.login_mobile_phonenumber_edt);
        loginMobileGetverifyBtn = (Button) findViewById(R.id.login_mobile_getverify_btn);
        loginMobileVerifyEdt = (EditText) findViewById(R.id.login_mobile_verify_edt);
        loginMobileLoginbtn = (Button) findViewById(R.id.login_mobile_loginbtn);

    }

    private void initControl() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 获取验证码的点击事件
        loginMobileGetverifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhone = loginMobilePhonenumberEdt.getText().toString();
                if (userPhone.length() != 11) {
                    ToastUtil.show(getApplicationContext(), "请输入正确的大陆手机号码");
                    return;
                }

                // 先进行查询此用户（手机号）是否存在
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("mobilePhoneNumber", userPhone);
                query.setLimit(1);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (list == null || list.size() == 0) {
                            // 证明账号不存在
                            ToastUtil.show(getApplicationContext(), "该手机号尚未注册");
                        } else {
                            mHandler.sendEmptyMessage(USER_NO_REGIST);
                        }
                    }
                });
            }
        });

        // 验证并登录的点击事件
        loginMobileLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currPhone = loginMobilePhonenumberEdt.getText().toString();
                String verifyCode = loginMobileVerifyEdt.getText().toString();

                if (TextUtils.isEmpty(currPhone) || TextUtils.isEmpty(verifyCode)) {
                    ToastUtil.show(getApplicationContext(), "手机号或者验证码不能为空！");
                    return;
                }

                BmobSMS.verifySmsCode(currPhone, verifyCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            // 验证通过

                            // 判断验证码是否过期
                            long currTime = System.currentTimeMillis();
                            long diffTime = currTime - sendTime;
                            if (diffTime > 5 * 60 * 1000) {
                                ToastUtil.show(getApplicationContext(), "验证码已过期，请重新获取验证码");
                            } else {
                                // 登陆通过
                                SpUtil.putBoolean(getApplicationContext(), ConstantValue.IS_LOGIN, true);
                                SpUtil.putString(getApplicationContext(), ConstantValue.USER_NAME, userPhone);
                                setResult(ConstantValue.MOBILE_LOGIN_OK);
                                finish();
                            }

                        } else {
                            // 验证失败
                            ToastUtil.show(getApplicationContext(), "输入的验证码有误，请重新核对后输入");
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取短信验证码的方法
     */
    private void getSMSCode() {
        // 获得验证码
        String verifyCode = OtherUtils.getSMSCodeUtil();

        BmobSMS.requestSMSCode(userPhone, "非洲大卖场：您的验证码是" + verifyCode + "，有效期为5分钟。", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    ToastUtil.show(getApplicationContext(), "验证码已发送成功，请您注意查收！");
                    loginMobileGetverifyBtn.setEnabled(false);
                    loginMobileGetverifyBtn.setBackgroundColor(Color.GRAY);
                    loginMobileGetverifyBtn.setText("重新获取");
                    sendTime = System.currentTimeMillis();
                }
            }
        });
    }

}
