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

/**
 * 登录页 忘记密码
 */

public class LoginPwdLoseActivity extends AppCompatActivity {


    private EditText mPhoneNumber;
    private Button mNext;
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
                if (s == null) {
                    mNext.setBackgroundColor(Color.parseColor("#c0c0c0"));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mNext.setBackgroundDrawable(getResources().getDrawable(R.color.login_orign));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 11) {
                    mNext.setBackgroundColor(Color.parseColor("#FE6A12"));
                    mNext.setEnabled(true);
                } else {
                    mNext.setBackgroundColor(Color.parseColor("#c0c0c0"));
                    mNext.setEnabled(false);
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mPhoneNumber.getText().toString();

                if (phone.length() != 11) {
                    ToastUtil.show(getApplicationContext(), "请输入正确的大陆手机号码");
                    return;
                }

                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("mobilePhoneNumber", phone);
                query.setLimit(1);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (list == null || list.size() == 0) {
                            // 没注册过
                            ToastUtil.show(getApplicationContext(), "此手机号尚未注册，请核对后重新输入");
                        } else {
                            Intent intent = new Intent(LoginPwdLoseActivity.this, ResetPwdActivity.class);
                            intent.putExtra(ConstantValue.FIND_PWD_PHONE, phone);
                            intent.putExtra(ConstantValue.USER_OBJECT_ID, list.get(0).getObjectId());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

    }

    private void initView() {
        mBack = (Button) findViewById(R.id.login_find_pwd_title_backbtn);
        mNext = (Button) findViewById(R.id.login_pwd_lose_next_btn);
        mPhoneNumber = (EditText) findViewById(R.id.login_find_pwd_edt);
    }

}
