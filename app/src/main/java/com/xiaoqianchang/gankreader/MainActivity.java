package com.xiaoqianchang.gankreader;

import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xiaoqianchang.gankreader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private FrameLayout flTitleMenu;
    private ViewPager viewPager;
    private ImageView ivTitleGank;
    private ImageView ivTitleOne;
    private ImageView ivTitleDou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initId();
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navigationView = mBinding.navView;
    }
}
