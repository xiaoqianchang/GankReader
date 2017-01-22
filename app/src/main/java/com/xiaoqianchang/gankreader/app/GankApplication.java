package com.xiaoqianchang.gankreader.app;

import android.app.Application;
import android.content.Context;

/**
 * $desc$
 * <p>
 * Created by Chang.Xiao on 2017/1/22.
 *
 * @version 1.0
 */

public class GankApplication extends Application {

    private static GankApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getInstance() {
        return application;
    }
}
