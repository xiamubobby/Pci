package com.wonders.xlab.pci.doctor.module.notification.adapter.bean;

import android.databinding.ObservableField;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersBean {
    public ObservableField<Long> recordTimeInMill = new ObservableField<>();
    public ObservableField<String> txtContent = new ObservableField<>("");
    public ObservableField<Boolean> selected = new ObservableField<>(false);
    public ObservableField<Boolean> radioVisible = new ObservableField<>(false);
}