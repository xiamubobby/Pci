package com.wonders.xlab.pci.doctor.module.packagemanage.adapter.bean;

import android.databinding.ObservableField;

/**
 * Created by hua on 16/4/10.
 */
public class GroupServiceBean {
    public ObservableField<String> packageId = new ObservableField<>();
    public ObservableField<String> packageIconUrl = new ObservableField<>();
    public ObservableField<String> packageName = new ObservableField<>();
    public ObservableField<Boolean> published = new ObservableField<>();
    public ObservableField<String> packageDesc = new ObservableField<>();
    public ObservableField<String> packageDescColor = new ObservableField<>();
}
