package com.wonders.xlab.common.manager;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by hua on 16/2/19.
 */
public class ImageViewManager {
    public static void setImageViewWithUrl(Context context, ImageView imageView, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl) || imageView == null || context == null) {
            return;
        }
        Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }
}
