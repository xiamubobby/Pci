package com.wonders.xlab.patient.module.doctors.detail.bean;

import android.databinding.ObservableField;

import com.wonders.xlab.patient.Constant;

/**
 * Created by hua on 16/3/18.
 */
public class DoctorGroupBasicInfoBean {
    public ObservableField<String> groupName = new ObservableField<>();
    public ObservableField<String> servingPeople = new ObservableField<>();
    public ObservableField<String> groupAvatar = new ObservableField<>(Constant.DEFAULT_PORTRAIT);
    public ObservableField<String> description = new ObservableField<>();
    public ObservableField<String> servedPeopleCount = new ObservableField<>();
    public ObservableField<String> jobTitle = new ObservableField<>();
    public ObservableField<String> department = new ObservableField<>();
    public ObservableField<String> hospital = new ObservableField<>();
    /**
     * 是否为医生小组
     */
    public ObservableField<Boolean> isMulti = new ObservableField<>(false);

}
