package com.xiaoqianchang.gankreader.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoqianchang.gankreader.utils.transform.GlideCircleTransform;

/**
 * $desc$
 * <p>
 * Created by Chang.Xiao on 2017/1/23.
 *
 * @version 1.0
 */

public class ImageLoadUtils {

    private static ImageLoadUtils instance;

    private ImageLoadUtils() {
    }

    public static ImageLoadUtils getInstance() {
        if (null == instance) {
            synchronized (ImageLoadUtils.class) {
                if (null == instance) {
                    instance = new ImageLoadUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 图片默认加载器
     *
     * @param url
     * @param imageView
     */
    public void displayDefault(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .into(imageView);
    }

    public void displayCircle(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }
}
