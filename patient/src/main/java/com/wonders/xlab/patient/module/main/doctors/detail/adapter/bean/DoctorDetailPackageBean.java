package com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean;

import android.databinding.ObservableField;

import com.wonders.xlab.patient.Constant;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailPackageBean {
    public ObservableField<String> packageId = new ObservableField<>();

    /**
     * 套餐名称
     */
    public ObservableField<String> name = new ObservableField<>();
    /**
     * 套餐图标
     */
    public ObservableField<String> iconUrl = new ObservableField<>(Constant.DEFAULT_PORTRAIT);
    /**
     * 套餐价格
     */
    public ObservableField<String> priceStr = new ObservableField<>();

    /**
     * 简介
     */
    public ObservableField<String> description = new ObservableField<>();

}
