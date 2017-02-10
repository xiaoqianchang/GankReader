package com.xiaoqianchang.gankreader.utils;

import android.content.res.Resources;

import com.xiaoqianchang.gankreader.app.GankApplication;

/**
 * 获取原生资源
 * <p>
 * Created by Chang.Xiao on 2017/2/10.
 *
 * @version 1.0
 */

public class CommonUtils {

    public static Resources getResoure() {
        return GankApplication.getInstance().getResources();
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }
}
