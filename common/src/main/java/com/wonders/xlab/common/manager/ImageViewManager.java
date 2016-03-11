package com.wonders.xlab.common.manager;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * Created by hua on 16/2/19.
 */
public class ImageViewManager {
    public static void setImageViewWithUrl(Context context, ImageView imageView, String imageUrl, @DrawableRes int placeHolder) {
        if (TextUtils.isEmpty(imageUrl) || imageView == null || context == null) {
            return;
        }
        DrawableRequestBuilder<String> builder = Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .centerCrop();
        if (-1 != placeHolder) {
            builder.placeholder(placeHolder);
        }
        builder.into(imageView);
    }
}
