package com.wonders.xlab.patient.module.doctors.detail.adapter.bean;

import android.databinding.ObservableField;

import com.wonders.xlab.patient.Constant;

/**
 * Created by hua on 16/3/16.
 * 个人医生详情中的所属小组
 */
public class DoctorDetailGroupOfDoctorBean {
    /**
     * 小组名称
     */
    public ObservableField<String> name = new ObservableField<>();
    /**
     * 小组头像
     */
    public ObservableField<String> groupPortraitUrl = new ObservableField<>(Constant.DEFAULT_PORTRAIT);
}
