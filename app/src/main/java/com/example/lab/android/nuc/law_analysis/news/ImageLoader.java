package com.example.lab.android.nuc.law_analysis.news;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageLoader {
    private static ImageLoader mInstance;

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public void with(Context context, Object imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl).into(imageView);
    }

    /**
     * 带默认图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public void withDefault(Context context, Object imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl).into(imageView);
    }

    /**
     * 圆角图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public void withRound(Context context, Object imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .into(imageView);
    }
}
