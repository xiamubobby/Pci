package com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean;

import android.databinding.ObservableField;

/**
 * Created by hua on 16/4/15.
 */
public class NotifiPackageApplyBean {
    public ObservableField<String> id = new ObservableField<>();
    public ObservableField<Long> recordTimeInMill = new ObservableField<>();
    public ObservableField<String> patientName = new ObservableField<>();
    public ObservableField<String> patientAge = new ObservableField<>();
    public ObservableField<String> patientGender = new ObservableField<>();
    public ObservableField<String> patientSurgeryHospital = new ObservableField<>();
    public ObservableField<String> applyPackageName = new ObservableField<>();
    public ObservableField<String> patientAvatarUrl = new ObservableField<>();
}
