package com.wonders.xlab.patient.module.home.adapter.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.patient.BR;

/**
 * Created by hua on 16/3/8.
 */
public class HomeItemBean extends BaseObservable {
    private String title;
    private int drawableResId;
    private int backgroundDrawableId;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
        notifyPropertyChanged(BR.drawableResId);
    }

    public int getBackgroundDrawableId() {
        return backgroundDrawableId;
    }

    public void setBackgroundDrawableId(int backgroundDrawableId) {
        this.backgroundDrawableId = backgroundDrawableId;
    }
}
