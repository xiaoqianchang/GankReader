package com.xiaoqianchang.gankreader.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.R;

/**
 * 扫码下载
 * 
 * Created by Chang.Xiao on 2017/2/7.
 * @version 1.0
 */
public class NavDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NavDownloadActivity.class);
        context.startActivity(intent);
    }
}
