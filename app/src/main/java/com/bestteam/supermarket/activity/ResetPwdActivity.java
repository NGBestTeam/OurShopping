package com.bestteam.supermarket.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.MD5Util;
import com.bestteam.supermarket.utils.OtherUtils;
import com.bestteam.supermarket.utils.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPwdActivity extends AppCompatActivity {
    /**
     * 点击完成时出现的进度条
     */
    private ProgressDialog mDialog;

    /**
     * 传入的用户号码
     */
    private String userPhone;

    /**
     * 验证码的输入框
     */
    private EditText mEt_yzm_reset;

    /**
     * 获取验证码的按钮
     */
    private Button mBt_verify_code_reset;

    /**
     * 设置密码的输入框
     */
    private EditText mEt_setpd_reset;

    /**
     * 确认密码的输入框
     */
    private EditText mEt_confirmpd_reset;

    /**
     * 发送验证码的时间
     */
    private long sendTime;

    /**
     * 完成按钮
     */
    private CardView mCd_regist_reset;

    /**
     * 短信发送成功的验证码：
     */
    private static final int SMS_SEND_SUCCESS = 40;

    /**
     * 获取验证码的点击事件的时间计算
     */
    private static final int VERIFY_CLICK = 41;

    /**
     * 当前用户的ObjectId
     */
    private String userObjectId;

    /**
     * 处理短信验证的方法
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SMS_SEND_SUCCESS:
                    // 发送成功：
                    // 使获取验证码的按钮倒计时2分钟后变成可点击
                    mHandler.sendEmptyMessageDelayed(VERIFY_CLICK, 2 * 60 * 1000);
                    break;
                case VERIFY_CLICK:
                    mBt_verify_code_reset.setEnabled(true);
                    mBt_verify_code_reset.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        initUI();
        // 开始发送短信
        sendSMSCode();
        initClick();
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("提示：");
        mDialog.setMessage("注册中，请稍后...");
        mDialog.setIcon(R.mipmap.login_loading);
        mDialog.setCancelable(false);

        userObjectId = getIntent().getStringExtra(ConstantValue.USER_OBJECT_ID);

        View login_register_title_id_reset = findViewById(R.id.login_register_title_id_reset);
        TextView tv_title_info = (TextView) login_register_title_id_reset.findViewById(R.id.tv_title_info);
        tv_title_info.setText("找回密码");

        TextView tv_send_info_reset = (TextView) findViewById(R.id.tv_send_info_reset);
        userPhone = getIntent().getStringExtra(ConstantValue.FIND_PWD_PHONE);
        String showPhone = userPhone.substring(3, 7);
        showPhone = userPhone.replace(showPhone, "****");
        tv_send_info_reset.append(showPhone);

        mEt_yzm_reset = (EditText) findViewById(R.id.et_yzm_reset);
        mBt_verify_code_reset = (Button) findViewById(R.id.bt_verify_code_reset);
        mEt_setpd_reset = (EditText) findViewById(R.id.et_setpd_reset);
        mEt_confirmpd_reset = (EditText) findViewById(R.id.et_confirmpd_reset);
        mCd_regist_reset = (CardView) findViewById(R.id.cd_regist_reset);
    }

    /**
     * 发送短信的方法
     */
    private void sendSMSCode() {
        // 获得验证码
        String verifyCode = OtherUtils.getSMSCodeUtil();

        BmobSMS.requestSMSCode(userPhone, "非洲大卖场：您的验证码是" + verifyCode + "，有效期为5分钟。", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    ToastUtil.show(getApplicationContext(), "验证码已发送成功，请您注意查收！");
                    mBt_verify_code_reset.setEnabled(false);
                    mBt_verify_code_reset.setText("重新获取");
                    mBt_verify_code_reset.setBackgroundColor(Color.GRAY);
                    sendTime = System.currentTimeMillis();
                    mHandler.sendEmptyMessage(SMS_SEND_SUCCESS);
                }
            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void initClick() {
        mBt_verify_code_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSCode();
            }
        });

        mCd_regist_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifyCode = mEt_yzm_reset.getText().toString();
                final String pd = mEt_setpd_reset.getText().toString();
                String confirmPd = mEt_confirmpd_reset.getText().toString();

                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtil.show(getApplicationContext(), "验证码不能为空");
                    return;
                }

                if (pd.length() < 6 || pd.length() > 12) {
                    ToastUtil.show(getApplicationContext(), "密码不能小于6位且不能大于12位");
                    return;
                }

                if (!pd.equals(confirmPd)) {
                    ToastUtil.show(getApplicationContext(), "两次输入的密码不一致！请核对后输入");
                    return;
                }

                mDialog.show();
                BmobSMS.verifySmsCode(userPhone, verifyCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            // 验证成功，进行修改密码
                            // 判断验证码是否过期
                            long currTime = System.currentTimeMillis();
                            long diffTime = currTime - sendTime;
                            if (diffTime > 5 * 60 * 1000) {
                                mDialog.dismiss();
                                ToastUtil.show(getApplicationContext(), "验证码已过期，请重新获取验证码");
                            } else {
                                // 进行修改密码
                                User user = new User();
                                String uPd = MD5Util.encoder(pd);
                                user.setPassword(uPd);
                                user.update(userObjectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        mDialog.dismiss();
                                        if (e == null) {
                                            // 修改成功
                                            ToastUtil.show(getApplicationContext(), "修改密码成功！");
                                            finish();
                                        } else {
                                            // 修改失败
                                            ToastUtil.show(getApplicationContext(), "修改失败，请稍后再试...");
                                        }
                                    }
                                });
                            }
                        } else {
                            // 验证失败
                            mDialog.dismiss();
                            ToastUtil.show(getApplicationContext(), "您输入的验证码有误，请核对后重新输入");
                        }
                    }
                });
            }
        });
    }
}
