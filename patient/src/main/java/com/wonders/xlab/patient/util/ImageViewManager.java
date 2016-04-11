package com.wonders.xlab.patient.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * Created by hua on 16/2/19.
 */
public class ImageViewManager {
    public final static int PLACE_HOLDER_EMPTY = -1;

    /**
     * @param context
     * @param imageView
     * @param imageUrl
     * @param placeHolder
     */
    public static void setImageViewWithUrl(Context context, ImageView imageView, String imageUrl,int placeHolder) {
        if (TextUtils.isEmpty(imageUrl) || imageView == null || context == null) {
            return;
        }
        DrawableRequestBuilder<String> builder = Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .centerCrop();
        if (PLACE_HOLDER_EMPTY != placeHolder) {
            builder.placeholder(placeHolder);
        }
        builder.into(imageView);
    }
}
