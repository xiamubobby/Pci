package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by hua on 16/4/8.
 */
public class GroupDoctorBean implements Parcelable {
    public ObservableField<String> doctorId = new ObservableField<>();
    public ObservableField<String> doctorImId = new ObservableField<>();
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
        if (o instanceof GroupDoctorBean) {
            GroupDoctorBean bean = (GroupDoctorBean) o;
            return this.doctorId.get().equals(bean.doctorId.get());
        }
        return false;
    }

    public GroupDoctorBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.doctorId);
        dest.writeSerializable(this.doctorImId);
        dest.writeSerializable(this.doctorAvatarUrl);
        dest.writeSerializable(this.doctorName);
        dest.writeSerializable(this.doctorHospital);
        dest.writeSerializable(this.doctorTitle);
        dest.writeSerializable(this.doctorDepartment);
        dest.writeSerializable(this.isSelected);
    }

    protected GroupDoctorBean(Parcel in) {
        this.doctorId = (ObservableField<String>) in.readSerializable();
        this.doctorImId = (ObservableField<String>) in.readSerializable();
        this.doctorAvatarUrl = (ObservableField<String>) in.readSerializable();
        this.doctorName = (ObservableField<String>) in.readSerializable();
        this.doctorHospital = (ObservableField<String>) in.readSerializable();
        this.doctorTitle = (ObservableField<String>) in.readSerializable();
        this.doctorDepartment = (ObservableField<String>) in.readSerializable();
        this.isSelected = (ObservableField<Boolean>) in.readSerializable();
    }

    public static final Creator<GroupDoctorBean> CREATOR = new Creator<GroupDoctorBean>() {
        @Override
        public GroupDoctorBean createFromParcel(Parcel source) {
            return new GroupDoctorBean(source);
        }

        @Override
        public GroupDoctorBean[] newArray(int size) {
            return new GroupDoctorBean[size];
        }
    };
}
