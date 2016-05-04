package com.wonders.xlab.patient.module.doctordetail.adapter.bean;

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

    public final static int STATUS_OUT_SERVICE = 2;
    public final static int STATUS_IN_SERVICE = 1;
    public final static int STATUS_NEVER_BUY = 0;
    /**
     * 0 未购买 1 购买 2过期
     */
    public ObservableField<Integer> orderStatus = new ObservableField<>();

}
