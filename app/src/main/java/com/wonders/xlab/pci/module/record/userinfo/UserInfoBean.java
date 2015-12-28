package com.wonders.xlab.pci.module.record.userinfo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.pci.BR;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoBean extends BaseObservable {
    private int viewType;
    private String label;
    private String value;

    public UserInfoBean(int viewType, String label, String value) {
        this.viewType = viewType;
        this.label = label;
        this.value = value;
    }

    @Bindable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        notifyPropertyChanged(BR.label);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
        notifyPropertyChanged(BR.viewType);
    }
}
