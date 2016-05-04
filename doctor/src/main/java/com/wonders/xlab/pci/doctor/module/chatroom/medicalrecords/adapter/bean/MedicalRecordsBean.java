package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.bean;

import android.databinding.ObservableField;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsBean {

    /**
     * 就诊时间
     */
    private ObservableField<Long> time;

    /**
     * 门诊标签
     */
    private ObservableField<String> tag;

    /**
     * 就诊医院
     */
    public ObservableField<String> hospitalName;
    /**
     * 就诊科室
     */
    public ObservableField<String> departmentName;

    /**
     * 就诊结果
     */
    public ObservableField<String> medicalResult;


    public ObservableField<String> getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(ObservableField<String> hospitalName) {
        this.hospitalName = hospitalName;
    }

    public ObservableField<String> getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(ObservableField<String> departmentName) {
        this.departmentName = departmentName;
    }

    public ObservableField<String> getMedicalResult() {
        return medicalResult;
    }

    public void setMedicalResult(ObservableField<String> medicalResult) {
        this.medicalResult = medicalResult;
    }

    public ObservableField<Long> getTime() {
        return time;
    }

    public void setTime(ObservableField<Long> time) {
        this.time = time;
    }

    public ObservableField<String> getTag() {
        return tag;
    }

    public void setTag(ObservableField<String> tag) {
        this.tag = tag;
    }
}
