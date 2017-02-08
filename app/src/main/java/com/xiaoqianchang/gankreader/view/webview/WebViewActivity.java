package com.xiaoqianchang.gankreader.view.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoqianchang.gankreader.R;

/**
 * webview 容器
 *
 * Features:
 * 点击相应控件:拨打电话、发送短信、发送邮件、上传图片、播放视频、进度条、返回网页上一层、显示网页标题
 *
 * Thanks to: https://github.com/youlookwhat/WebViewStudy
 * 
 * Created by Chang.Xiao on 2017/2/8.
 * @version 1.0
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }
}
