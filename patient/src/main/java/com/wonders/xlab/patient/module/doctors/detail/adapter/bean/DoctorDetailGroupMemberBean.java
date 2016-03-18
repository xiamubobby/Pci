package com.wonders.xlab.patient.module.doctors.detail.adapter.bean;

import android.databinding.ObservableField;

import com.wonders.xlab.patient.Constant;

/**
 * Created by hua on 16/3/16.
 * 小组详情中的小组成员
 */
public class DoctorDetailGroupMemberBean {
    public ObservableField<String> doctorId = new ObservableField<>();

    /**
     * 组员姓名
     */
    public ObservableField<String> name = new ObservableField<>();
    /**
     * 组员头像
     */
    public ObservableField<String> portraitUrl = new ObservableField<>(Constant.DEFAULT_PORTRAIT);
    /**
     * 职称
     */
    public ObservableField<String> title = new ObservableField<>();

}
