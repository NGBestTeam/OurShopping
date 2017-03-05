package com.bestteam.supermarket.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.utils.CommonUrl;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 广告进入界面
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * ViewPager的适配器
     */
    private VpAdapter mVpAdapter = new VpAdapter();

    /**
     * ViewPager的图片数据
     */
    private List<ImageView> mVpImgs = new ArrayList<>();

    /**
     * ViewPager的图片id资源
     */
    private int[] mVpImgsId = {R.mipmap.splash_enter_1, R.mipmap.splash_enter_2, R.mipmap.splash_enter_3};

    /**
     * ViewPager的指示器
     */
    private LinearLayout mLinear_spactivity;

    /**
     * 进入APP的按钮
     */
    private ImageView mEnterHomeIv;

    /*
     * -------------------------------------------------------------------------------------------------------------------
     */

    /**
     * 进入App的时间
     */
    private long startTime;

    /**
     * 当前程序版本
     */
    private int mLocalVersionCode;

    /**
     * 新版本的介绍
     */
    private String mVersionDes;

    /**
     * 新版本的下载地址
     */
    private String mDwonloadUrl;

    /*
     * -------------------------------------------------------------------------------------------------------------------
     */

    /**
     * 进入主界面的时间
     */
    private static final int ENTERHOMETIME = 3000;

    /**
     * 进入主界面的状态码
     */
    private static final int ENTER_HOME_CODE = 0;

    /**
     * 弹出更新通知的状态码
     */
    private static final int UPDATE_VERSION_DIALOG_CODE = 1;

    /**
     * Json解析异常的状态码
     */
    private static final int JSONERROR_CODE = 3;

    /**
     * 下载APK成功的状态码
     */
    private static final int APK_DOWNLOAD_SUCCESS_CODE = 4;

    /**
     * 下载APK失败的状态码
     */
    private static final int APK_DOWNLOAD_FAIL_CODE = 5;

    /**
     * APK下载成功后的安装包路径
     */
    private String mApkPath;

    /**
     * 获取写入SD卡的权限申请码
     */
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 100;

    /**
     * 用来处理更新以及进入时间各种时间的handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENTER_HOME_CODE:
                    enterHome();
                    break;
                case UPDATE_VERSION_DIALOG_CODE:
                    showUpdateDialog();
                    break;
                case JSONERROR_CODE:
                    enterHome();
                    break;
                case APK_DOWNLOAD_SUCCESS_CODE:
                    installApk();
                    break;
                case APK_DOWNLOAD_FAIL_CODE:
                    enterHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次进入本程序
        boolean first_enter_app = SpUtil.getBoolean(this, ConstantValue.FIRST_ENTER_APP, true);
        if (first_enter_app) {
            SpUtil.putBoolean(this, ConstantValue.FIRST_ENTER_APP, false);
            setContentView(R.layout.activity_splash_first_enter);

            initFirstEnterUI();
            initFirstEnterData();
        } else {
            setContentView(R.layout.activity_splash);
            checkCurrentPermission();
            startTime = System.currentTimeMillis();
        }

    }

    /**
     * 首次进入页面后初始化UI
     */
    private void initFirstEnterUI() {
        ViewPager vp_spactivity = (ViewPager) findViewById(R.id.vp_spactivity);
        mLinear_spactivity = (LinearLayout) findViewById(R.id.linear_spactivity);
        mEnterHomeIv = (ImageView) findViewById(R.id.iv_enter_home);

        // 设置适配器
        vp_spactivity.setAdapter(mVpAdapter);

        // 设置监听
        vp_spactivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mVpImgs.size(); i++) {
                    ImageView img = (ImageView) mLinear_spactivity.getChildAt(i);

                    if (position == mVpImgs.size() - 1) {
                        mEnterHomeIv.setVisibility(View.VISIBLE);
                    }

                    if (i == position) {
                        img.setImageResource(R.mipmap.page_now);
                    } else {
                        img.setImageResource(R.mipmap.page);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mEnterHomeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterHome();
            }
        });
    }

    /**
     * 首次进入页面后初始化数据
     */
    private void initFirstEnterData() {
        for (int i = 0; i < mVpImgsId.length; i++) {
            // 初始化viewpager的大图
            ImageView img = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource(mVpImgsId[i]);
            mVpImgs.add(img);

            // 初始化指示器
            ImageView imgPoint = new ImageView(this);
            imgPoint.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgPoint.setPadding(40, 0, 40, 0);
            if (i == 0) {
                imgPoint.setImageResource(R.mipmap.page_now);
            } else {
                imgPoint.setImageResource(R.mipmap.page);
            }
            mLinear_spactivity.addView(imgPoint);
        }
        mVpAdapter.notifyDataSetChanged();
    }

    /**
     * 获取运行时权限
     */
    private void checkCurrentPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
        } else {
            checkAppUpdate();
        }
    }

    /**
     * 申请权限后的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkCurrentPermission();
                } else {
                    Toast.makeText(getApplicationContext(), "拒绝权限后部分功能无法使用哦!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 检查App版本是否更新的方法
     */
    private void checkAppUpdate() {
        mLocalVersionCode = getVersionCode();
        checkVersion();
    }

    /**
     * 获取当前程序版本号的方法
     *
     * @return 如果非0则代表获取成功，0代表异常
     */
    private int getVersionCode() {
        PackageManager manager = getPackageManager();

        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取远程版本号的方法
     */
    private void checkVersion() {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(CommonUrl.UPDATE_APP_URL);
        builder.method("GET", null);
        Request request = builder.build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                long time = getTime();
                if (time < ENTERHOMETIME) {
                    mHandler.sendEmptyMessageDelayed(ENTER_HOME_CODE, ENTERHOMETIME - time);
                } else {
                    mHandler.sendEmptyMessage(ENTER_HOME_CODE);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Message message = Message.obtain();
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);

                    // 获取版本号及版本信息，新版本下载地址等
                    mVersionDes = jsonObject.getString("versionDes");
                    String versionCode = jsonObject.getString("versionCode");
                    mDwonloadUrl = jsonObject.getString("downloadUrl");

                    // 对比版本号：
                    if (mLocalVersionCode < Integer.parseInt(versionCode)) {
                        // 提示用户更新，弹出对话框
                        message.what = UPDATE_VERSION_DIALOG_CODE;
                    } else {
                        message.what = ENTER_HOME_CODE;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = JSONERROR_CODE;
                } finally {
                    long time = getTime();
                    if (time < ENTERHOMETIME) {
                        mHandler.sendMessageDelayed(message, ENTERHOMETIME - time);
                    } else {
                        mHandler.sendMessage(message);
                    }
                }
            }
        });
    }

    /**
     * 弹出对话框，提示用户更新
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.update_dialog);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);

        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHandler.sendEmptyMessage(ENTER_HOME_CODE);
            }
        });

        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mHandler.sendEmptyMessage(ENTER_HOME_CODE);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 用户更新APK的方法
     */
    private void downloadAPK() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            final String path = Environment.getExternalStorageDirectory() + "/myapp/";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(mDwonloadUrl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mHandler.sendEmptyMessage(ENTER_HOME_CODE);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    BufferedInputStream bis = null;
                    BufferedOutputStream bos = null;
                    Message message = Message.obtain();

                    try {
                        String appName = mDwonloadUrl.substring(mDwonloadUrl.lastIndexOf("/") + 1);
                        mApkPath = path + appName;

                        bis = new BufferedInputStream(response.body().byteStream());
                        bos = new BufferedOutputStream(new FileOutputStream(mApkPath));
                        byte[] buffer = new byte[2048];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            bos.write(buffer, 0, len);
                            bos.flush();
                        }

                        message.what = APK_DOWNLOAD_SUCCESS_CODE;

                    } catch (IOException e) {
                        e.printStackTrace();
                        message.what = APK_DOWNLOAD_FAIL_CODE;
                    } finally {
                        if (bis != null) {
                            bis.close();
                        }

                        if (bos != null) {
                            bos.close();
                        }

                        mHandler.sendMessage(message);
                    }
                }
            });
        } else {
            mHandler.sendEmptyMessage(ENTER_HOME_CODE);
        }
    }

    /**
     * 安装对应APK
     */
    private void installApk() {
        File file = new File(mApkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        // 设置文件类型和格式
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    /**
     * 回应上面的startActivityForResult
     * 安装界面如果取消或者失败将调用此方法
     * 直接进入主界面
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取当前时间与进入界面时间的时间差
     * @return 返回时间差
     */
    private long getTime() {
        long currentTime = System.currentTimeMillis();
        return currentTime - startTime;
    }

    /**
     * 进入APP主界面的方法
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * Viewpager的适配器
     */
    private class VpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mVpImgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mVpImgs.get(position));
            return mVpImgs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mVpImgs.get(position));
        }
    }

    /**
     * 监听退出键的方法
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
    }
}
