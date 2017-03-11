package com.bestteam.supermarket.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterInfoActivity extends AppCompatActivity {

    /**
     * 用户的手机号
     */
    private String userPhone;

    /**
     * 验证码的输入框
     */
    private EditText mEt_yzm;

    /**
     * 获取验证码的按钮
     */
    private Button mBt_get_verify_code;

    /**
     * 设置密码的输入框
     */
    private EditText mEt_setpd;

    /**
     * 确认密码的输入框
     */
    private EditText mEt_confirmpd;

    /**
     * 发送验证码的时间
     */
    private long sendTime;

    /**
     * 短信发送成功的验证码：
     */
    private static final int SMS_SEND_SUCCESS = 30;

    /**
     * 获取验证码的点击事件的时间计算
     */
    private static final int VERIFY_CLICK = 31;

    /**
     * 注册时的进度条
     */
    private ProgressDialog mDialog;

    /**
     * 注册按钮
     */
    private CardView mCd_regist;

    /**
     * 处理短信验证的Handler
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
                    mBt_get_verify_code.setEnabled(true);
                    mBt_get_verify_code.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        initUI();
        // 开始发送短信
        sendSMSCode();
        initClick();
    }

    /**
     * 初始化
     */
    private void initUI() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("提示：");
        mDialog.setMessage("注册中，请稍后...");
        mDialog.setIcon(R.mipmap.login_loading);
        mDialog.setCancelable(false);

        // 得到传入的手机号：
        userPhone = getIntent().getStringExtra(ConstantValue.USER_REGIST_PHONE);

        String showPhone = userPhone.substring(3, 7);
        showPhone = userPhone.replace(showPhone, "****");

        TextView tv_send_info = (TextView) findViewById(R.id.tv_send_info);
        tv_send_info.append(showPhone);

        mEt_yzm = (EditText) findViewById(R.id.et_yzm);
        mBt_get_verify_code = (Button) findViewById(R.id.bt_get_verify_code);
        mEt_setpd = (EditText) findViewById(R.id.et_setpd);
        mEt_confirmpd = (EditText) findViewById(R.id.et_confirmpd);

        mCd_regist = (CardView) findViewById(R.id.cd_regist);
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
                    mBt_get_verify_code.setEnabled(false);
                    mBt_get_verify_code.setText("重新获取");
                    mBt_get_verify_code.setBackgroundColor(Color.GRAY);
                    sendTime = System.currentTimeMillis();
                    mHandler.sendEmptyMessage(SMS_SEND_SUCCESS);
                } else {
                    Log.d("AA", e.getMessage() + "\n" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 初始化所有监听事件
     */
    private void initClick() {
        // 获取验证码的监听
        mBt_get_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重新发送：
                sendSMSCode();
            }
        });

        // 注册的监听
        mCd_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifyCode = mEt_yzm.getText().toString();
                final String userPd = mEt_setpd.getText().toString();
                String confirmPd = mEt_confirmpd.getText().toString();

                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtil.show(getApplicationContext(), "验证码不能为空！");
                    return;
                }

                if (userPd.length() < 6 && userPd.length() > 12) {
                    ToastUtil.show(getApplicationContext(), "密码不能小于6位且不能大于12位");
                    return;
                }

                if (TextUtils.isEmpty(userPd) || TextUtils.isEmpty(confirmPd)) {
                    ToastUtil.show(getApplicationContext(), "密码不能为空！");
                    return;
                }

                if (!userPd.equals(confirmPd)) {
                    ToastUtil.show(getApplicationContext(), "两次输入的密码不一致！请核对后输入");
                    return;
                }

                mDialog.show();

                BmobSMS.verifySmsCode(userPhone, verifyCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            // 验证通过

                            // 判断验证码是否过期
                            long currTime = System.currentTimeMillis();
                            long diffTime = currTime - sendTime;
                            if (diffTime > 5 * 60 * 1000) {
                                mDialog.dismiss();
                                ToastUtil.show(getApplicationContext(), "验证码已过期，请重新获取验证码");
                            } else {
                                // 进行注册
                                final User user = new User();
                                user.setUsername(userPhone);
                                String uPd = MD5Util.encoder(userPd);
                                user.setPassword(uPd);
                                user.setSex("男");
                                user.setMobilePhoneNumber(userPhone);

                                // 进行设置头像
                                String photoName = userPhone + "_photo.jpg";
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo);
                                File file = new File(Environment.getExternalStorageDirectory() + "/Bmob/fzdmc/");
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                File tempFile = new File(file.getAbsolutePath() + "/" + photoName);

                                try {
                                    FileOutputStream fos = new FileOutputStream(tempFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    fos.flush();
                                    fos.close();
                                } catch (Exception e1) {
                                    mDialog.dismiss();
                                    e1.printStackTrace();
                                }

                                final BmobFile photoFile = new BmobFile(tempFile);
                                photoFile.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            user.setPhoto(photoFile);
                                            ToastUtil.show(getApplicationContext(), "头像上传成功！");
                                        } else {
                                            Log.d("AA", "头像上传失败：" + e.getMessage()
                                                    + "\n" + e.getErrorCode());
                                            ToastUtil.show(getApplicationContext(), "头像上传失败：" + e.getMessage()
                                                    + "\n" + e.getErrorCode());
                                        }

                                        user.signUp(new SaveListener<User>() {
                                            @Override
                                            public void done(User user, BmobException e) {
                                                mDialog.dismiss();
                                                if (e == null) {
                                                    // 注册成功
                                                    ToastUtil.show(getApplicationContext(), "注册成功！赶紧去登陆吧");
                                                    finish();
                                                } else {
                                                    // 注册失败
                                                    ToastUtil.show(getApplicationContext(), "注册失败：" + e.getMessage() + "\n"
                                                            + e.getErrorCode());
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        } else {
                            mDialog.dismiss();
                            ToastUtil.show(getApplicationContext(), "您输入的验证码有误，请核对后重新输入");
                        }
                    }
                });
            }
        });
    }

}
