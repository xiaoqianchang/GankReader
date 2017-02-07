package com.xiaoqianchang.gankreader.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.R;

/**
 * 项目主页
 * 
 * Created by Chang.Xiao on 2017/2/7.
 * @version 1.0
 */
public class NavHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home_page);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NavHomePageActivity.class);
        context.startActivity(intent);
    }
}
