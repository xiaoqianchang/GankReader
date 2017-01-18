package com.xiaoqianchang.gankreader;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.databinding.ActivitySplashBinding;

/**
 * 启动导航图
 * 
 * Created by Chang.Xiao on 2017/1/18.
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mDataBing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }
}
