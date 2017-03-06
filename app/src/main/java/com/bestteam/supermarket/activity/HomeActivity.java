package com.bestteam.supermarket.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bestteam.supermarket.R;
import com.bestteam.supermarket.fragment.HomeFragment;

/**
 * 主界面
 */

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fManager = getSupportFragmentManager();
        fManager.beginTransaction().replace(R.id.fl_home,new HomeFragment()).commit();

    }
}
