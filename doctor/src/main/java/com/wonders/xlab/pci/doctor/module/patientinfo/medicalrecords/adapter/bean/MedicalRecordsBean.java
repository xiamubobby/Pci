package com.wonders.xlab.pci.doctor.module.patientinfo.medicalrecords.adapter.bean;

/**
 * Created by jimmy on 16/5/3.
 */
public class MedicalRecordsBean {

    /**
     * 就诊时间
     */
    private String time;

    /**
     * 门诊标签
     */
    private String tag;

    /**
     * 就诊医院
     */
    public String hospitalName;
    /**
     * 就诊科室
     */
    public String departmentName;

    /**
     * 就诊结果
     */
    public String medicalResult;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMedicalResult() {
        return medicalResult;
    }

    public void setMedicalResult(String medicalResult) {
        this.medicalResult = medicalResult;
    }
}