package com.xiaoqianchang.gankreader.utils;

import android.view.View;

/**
 * 避免在1秒内触发多次点击
 * <p>
 * Created by Chang.Xiao on 2017/4/21.
 *
 * @version 1.0
 */

public abstract class OnPerfectClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime;
    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        int mId = v.getId();
        if (mId != id) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
