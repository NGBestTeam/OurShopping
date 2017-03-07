package com.bestteam.supermarket.utils;/**
 * Created by Xu on 2017/3/7.
 */

import android.view.View;
import android.widget.Button;

import com.bestteam.supermarket.R;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */
public class TitleEvenUtils {

    private View mView;
    public Button saobtn;
    public Button searchBtn;
    public Button msgBtn;

    public TitleEvenUtils(View view) {
        mView = view;
        saobtn= (Button) view.findViewById(R.id.title_bar_sao);
        searchBtn= (Button) view.findViewById(R.id.title_bar_search);
        msgBtn= (Button) view.findViewById(R.id.title_bar_message);
    }

}
