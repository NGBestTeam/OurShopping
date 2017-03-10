package com.bestteam.supermarket.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.bean.User;
import com.bestteam.supermarket.utils.ConstantValue;
import com.bestteam.supermarket.utils.SpUtil;
import com.bestteam.supermarket.utils.ToastUtil;
import com.bestteam.supermarket.view.CustomPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ChangeInfoActivity extends AppCompatActivity implements CustomPopupWindow.OnItemClickListener {

    private EditText mEt;
    private RadioButton mRbMan;
    private RadioButton mRbWoman;
    private Button mBtnCommit;
    private ImageView mUserImg;
    private RelativeLayout layout;
    private CustomPopupWindow mPop;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        initView();
    }


    private void initView() {
        mEt = (EditText) findViewById(R.id.change_et_id);
        mRbMan = (RadioButton) findViewById(R.id.rb_man);
        mRbWoman = (RadioButton) findViewById(R.id.rb_woman);
        mBtnCommit = (Button) findViewById(R.id.change_btn_commit);
        mUserImg = (ImageView) findViewById(R.id.change_user_img);
        layout= (RelativeLayout) findViewById(R.id.change_layout_id);
        mPop=new CustomPopupWindow(this);
        mPop.setOnItemClickListener(this);
        //保存图片的路径
        filePath=Environment.getExternalStorageDirectory().
                getAbsolutePath()+"/a.png";




        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPop.showAtLocation(ChangeInfoActivity.this.findViewById(R.id.activity_change_info), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            }
        });

        //读取保存的位图（图片）
        Bitmap bitmap1= readImg();
        if(bitmap1!=null){
            mUserImg.setImageBitmap(bitmap1);
        }else{
            return;
        }


        BmobQuery<User> query=new BmobQuery<>();
        query.getObject(SpUtil.getString(this, ConstantValue.USER_OBJECT_ID,""), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user!=null){
                    mEt.setHint(user.getUsername());
                    BmobFile file=user.getPhoto();
                    File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
                    if (!saveFile.exists()){
                        saveFile.mkdirs();
                    }
                    file.download(saveFile, new DownloadFileListener() {
                        @Override
                        public void done(String s, BmobException e) {
                            Bitmap bitmap= BitmapFactory.decodeFile(s);
                            mUserImg.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onProgress(Integer integer, long l) {


                        }
                    });

                }
            }
        });

    }

    public void edit(View view) {
        mEt.setEnabled(true);
        mRbMan.setEnabled(true);
        mRbWoman.setEnabled(true);
        mBtnCommit.setVisibility(View.VISIBLE);
    }

    public void commit(View view) {
        final User user=new User();
        user.setUsername(mEt.getText().toString());
        user.setPassword(mEt.getText().toString());
        if (mRbMan.isChecked()){
            user.setSex("男");
        }else {
            user.setSex("女");
        }

        String photoName = mEt.getText().toString() + "_photo.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
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
            e1.printStackTrace();
        }

        final BmobFile photoFile = new BmobFile(tempFile);
        user.setPhoto(photoFile);
        photoFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    ToastUtil.show(getApplicationContext(), "上传成功！");
                } else {
                    Log.d("AA", "上传失败：" + e.getMessage()
                            + "\n" + e.getErrorCode());
                    ToastUtil.show(getApplicationContext(), "上传失败：" + e.getMessage()
                            + "\n" + e.getErrorCode());
                }

            }
        });

        user.signUp(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Log.e("info","成功");
                }else {
                    Log.e("info","失败"+e.getMessage()+e.getErrorCode());
                }
            }
        });

//        mEt.setEnabled(false);
//        mRbMan.setEnabled(false);
//        mRbWoman.setEnabled(false);
//        mBtnCommit.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1) {
            if (data != null) {
                Uri uri = data.getData();
                getImg(uri);
            } else {
                return;
            }
        }
        if (requestCode == 0x2) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                //得到图片
                Bitmap bitmap = bundle.getParcelable("data");
                //保存到图片到本地
                saveImg(bitmap);
                //设置图片
                mUserImg.setImageBitmap(bitmap);
            } else {
                return;
            }
        }
        if (requestCode == 0x3) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                mUserImg.setImageBitmap(bitmap);
            } else {
                return;
            }
        }
    }
    //读取位图（图片）
    private Bitmap readImg() {
        File mfile = new File(filePath);
        Bitmap bm = null;
        if (mfile.exists()) {        //若该文件存在
            bm = BitmapFactory.decodeFile(filePath);
        }
        return bm;
    }

    //保存图片到本地，下次直接读取
    private void saveImg(Bitmap mBitmap)  {
        File f = new File(filePath);
        try {
            //如果文件不存在，则创建文件
            if(!f.exists()){
                f.createNewFile();
            }
            //输出流
            FileOutputStream out = new FileOutputStream(f);
            /** mBitmap.compress 压缩图片
             *
             *  Bitmap.CompressFormat.PNG   图片的格式
             *   100  图片的质量（0-100）
             *   out  文件输出流
             */
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this,f.getAbsolutePath().toString(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getImg(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            //从输入流中解码位图
             Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //保存位图
             mUserImg.setImageBitmap(bitmap);
            //cutImg(uri);
            //关闭流
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //裁剪图片
    private void cutImg(Uri uri) {
        if (uri != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //true:出现裁剪的框
            intent.putExtra("crop", "true");
            //裁剪宽高时的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //裁剪后的图片的大小
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("return-data", true);  // 返回数据
            intent.putExtra("output", uri);
            intent.putExtra("scale", true);
            startActivityForResult(intent, 0x2);
        } else {
            return;
        }
    }

    @Override
    public void setOnItemClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.id_btn_take_photo:
                Toast.makeText(ChangeInfoActivity.this,"照相",Toast.LENGTH_LONG).show();
                intent = new Intent();
                //MediaStore.ACTION_IMAGE_CAPTURE  调用系统的照相机
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0x3);
                break;
            case R.id.id_btn_select:
                //打开手机的图库;
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0x1);
                break;
            case R.id.id_btn_cancelo:
                mPop.dismiss();
                break;
        }

    }

    public void back(View view) {
        finish();

    }
}