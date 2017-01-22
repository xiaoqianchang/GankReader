package com.xiaoqianchang.gankreader;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.databinding.ActivitySplashBinding;
import com.xiaoqianchang.gankreader.utils.GlideImageLoader;

import java.util.Arrays;
import java.util.List;

/**
 * 启动导航图
 * 
 * Created by Chang.Xiao on 2017/1/18.
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mDataBing;
    private List<Integer> mDrawables = Arrays.asList(R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBing = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        mDataBing.bannerSplash.setImages(mDrawables).setImageLoader(new GlideImageLoader()).start();
    }
}
