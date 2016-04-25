package com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean;

import android.databinding.ObservableField;

import java.util.List;

/**
 * Created by hua on 16/3/16.
 * 个人医生详情中的所属小组
 */
public class DoctorDetailGroupOfDoctorBean {
    public ObservableField<String> groupId = new ObservableField<>();
    public ObservableField<String> ownerId = new ObservableField<>();

    /**
     * 小组名称
     */
    public ObservableField<String> name = new ObservableField<>();
    /**
     * 小组头像
     */
    public ObservableField<List<String>> groupPortraitUrls = new ObservableField<>();
}
