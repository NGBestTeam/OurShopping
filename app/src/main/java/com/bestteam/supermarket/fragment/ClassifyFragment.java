package com.bestteam.supermarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.supermarket.R;

/**
 * Created by myself on 17/3/6.
 */

public class ClassifyFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment,container,false);
        TextView tv= (TextView) view.findViewById(R.id.tv);
        tv.setText("分类");
        return view;
    }
}
