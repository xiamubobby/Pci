package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean;

import android.databinding.ObservableField;
import android.text.TextUtils;

/**
 * Created by hua on 16/4/8.
 */
public class GroupInviteDoctorBean {
    public ObservableField<String> doctorId = new ObservableField<>();
    public ObservableField<String> doctorAvatarUrl = new ObservableField<>();
    public ObservableField<String> doctorName = new ObservableField<>();
    public ObservableField<String> doctorHospital = new ObservableField<>();
    public ObservableField<String> doctorTitle = new ObservableField<>();
    public ObservableField<String> doctorDepartment = new ObservableField<>();
    public ObservableField<Boolean> isSelected = new ObservableField<>(false);

    @Override
    public boolean equals(Object o) {
        if (null == o || doctorId == null || TextUtils.isEmpty(doctorId.get())) {
            return false;
        }
        if (o instanceof GroupInviteDoctorBean) {
            GroupInviteDoctorBean bean = (GroupInviteDoctorBean) o;
            return this.doctorId.get().equals(bean.doctorId.get());
        }
        return false;
    }
}
