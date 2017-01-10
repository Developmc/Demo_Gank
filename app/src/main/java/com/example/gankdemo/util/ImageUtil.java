package com.example.gankdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**加载图片
 * Created by clement on 17/1/10.
 */

public class ImageUtil {
    public static void displayBigImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
