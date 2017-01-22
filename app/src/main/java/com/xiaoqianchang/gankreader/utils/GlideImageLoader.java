package com.xiaoqianchang.gankreader.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoqianchang.gankreader.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Glide 图片加载器
 * <p>
 * Created by Chang.Xiao on 2017/1/22.
 *
 * @version 1.0
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .crossFade(1000)
                .into(imageView);
    }
}
