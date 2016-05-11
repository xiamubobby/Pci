package com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.bean;

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
    private int officeType;

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

    public String getOfficeType() {
        switch (officeType) {
            case 1:
                return "普通门诊";
            case 2:
                return "专科门诊";
            case 3:
                return "专家门诊";
            case 4:
                return "特需门诊";
            case 5:
                return "转病门诊";
            case 6:
                return "急诊";
            case 7:
                return "体检";
            case 8:
                return "其他";
            default:
                return "其他";
        }
    }

    public void setOfficeType(int officeType) {
        this.officeType = officeType;
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
