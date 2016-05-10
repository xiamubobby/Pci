package com.wonders.xlab.patient.module.healthrecord.surgicalhistory.adapter.bean;

/**
 * Created by jimmy on 16/5/3.
 */
public class SurgicalHistoryBean {

    /**
     * 手术史开始时间
     */
    public String surgicalHistoryTime;
    /**
     * 医院名称
     */
    public String hospitalName;
    /**
     * 科室名称
     */
    public String departmentName;

    /**
     * 出院诊断
     */
    public String leaveHospitalDiagnostics;
    /**
     * 医生建议
     */
    public String doctorSuggestion;

    public String getSurgicalHistoryTime() {
        return surgicalHistoryTime;
    }

    public void setSurgicalHistoryTime(String surgicalHistoryTime) {
        this.surgicalHistoryTime = surgicalHistoryTime;
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

    public String getLeaveHospitalDiagnostics() {
        return leaveHospitalDiagnostics;
    }

    public void setLeaveHospitalDiagnostics(String leaveHospitalDiagnostics) {
        this.leaveHospitalDiagnostics = leaveHospitalDiagnostics;
    }

    public String getDoctorSuggestion() {
        return doctorSuggestion;
    }

    public void setDoctorSuggestion(String doctorSuggestion) {
        this.doctorSuggestion = doctorSuggestion;
    }
}
