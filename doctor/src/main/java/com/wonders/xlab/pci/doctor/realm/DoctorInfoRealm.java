package com.wonders.xlab.pci.doctor.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/4/10.
 */
public class DoctorInfoRealm extends RealmObject {
    private String doctorId;
    private String doctorName;
    private String doctorAvatarUrl;
    private String doctorTel;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorAvatarUrl() {
        return doctorAvatarUrl;
    }

    public void setDoctorAvatarUrl(String doctorAvatarUrl) {
        this.doctorAvatarUrl = doctorAvatarUrl;
    }

    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }
}
