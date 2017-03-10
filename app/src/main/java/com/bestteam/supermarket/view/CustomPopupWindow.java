package com.bestteam.supermarket.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bestteam.supermarket.R;

/**
 * Created by myself on 17/3/10.
 */

public class CustomPopupWindow extends PopupWindow implements OnClickListener{
    private Button btnTakePhoto, btnSelect, btnCancel;
    private View mPopView;
    private OnItemClickListener mListener;


    public CustomPopupWindow(Context context) {
        super(context);
        initView(context);
        setPopupWindow();
        btnTakePhoto.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        
    }
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height=mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y= (int) event.getY();
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (y<height){
                        dismiss();
                    }
                }

                return true;
            }
        });



    }

    /**
     * 初始化控件
     * @param context
     */
    private void initView(Context context) {
        mPopView= LayoutInflater.from(context).inflate(R.layout.item_popowindow,null,false);
        btnTakePhoto= (Button) mPopView.findViewById(R.id.id_btn_take_photo);
        btnSelect= (Button) mPopView.findViewById(R.id.id_btn_select);
        btnCancel= (Button) mPopView.findViewById(R.id.id_btn_cancelo);

    }

    /**
     * 定义一个接口，公布出去，在activity中操作按钮的点击事件
     *
     */
    public  interface  OnItemClickListener{
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
    @Override
    public void onClick(View v) {
        if (mListener!=null){
            mListener.setOnItemClick(v);
        }

    }
}
