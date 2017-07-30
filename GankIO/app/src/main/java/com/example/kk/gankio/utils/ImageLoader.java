package com.example.kk.gankio.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by kk on 2017/7/22.
 */

public class ImageLoader {

    public static void load(Context context, String uri, ImageView image, int placeHold){
        Glide.with(context).load(uri)
                .crossFade()
                .placeholder(placeHold)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .into(image);
    }

    public static void load(Context context, int resId, ImageView image) {
        Glide.with(context)
                .load(resId)
                .crossFade()
                .into(image);
    }
}
