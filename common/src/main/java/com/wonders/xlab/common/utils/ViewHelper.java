package com.wonders.xlab.common.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by tangmingjian on 15/12/6.
 */
public class ViewHelper {

    public static void setVisibility(@NonNull View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public static void setText(@NonNull View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public static void exchangeText(@NonNull TextView first, @NonNull TextView second) {
        String temp = first.getText().toString();
        first.setText(second.getText().toString());
        second.setText(temp);
    }

    public static void exchangeVisibility(@NonNull View first, @NonNull View sencond) {
        if (first.getVisibility() == sencond.getVisibility()) return;

        int temp = first.getVisibility();
        first.setVisibility(sencond.getVisibility());
        sencond.setVisibility(temp);
    }

    public static void shakeEdit(@NonNull View view, Context context) {
        if (view instanceof EditText) view.requestFocus();
        ObjectAnimator.ofFloat(view, "translationY", 0f, 7f, 0f, -7f, 0f, 7f, 0f, -7f, 0f).setDuration(300).start();
    }
}
