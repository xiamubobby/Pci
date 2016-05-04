package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.bean;

import android.databinding.ObservableField;

/**
 * Created by jimmy on 16/5/3.
 */
public class SurgicalHistoryBean {

    /**
     * 手术史开始时间
     */
    public ObservableField<Long> surgicalHistoryBeginTime;
    /**
     * 手术史结束时间
     */
    public ObservableField<Long> surgicalHistoryEndTime;
    /**
     * 医院名称
     */
    public ObservableField<String> hospitalName;
    /**
     * 科室名称
     */
    public ObservableField<String> departmentName;

    /**
     * 出院诊断
     */
    public ObservableField<String> leaveHospitalDiagnostics;
    /**
     * 医生建议
     */
    public ObservableField<String> doctorSuggestion;

    public ObservableField<Long> getSurgicalHistoryBeginTime() {
        return surgicalHistoryBeginTime;
    }

    public void setSurgicalHistoryBeginTime(ObservableField<Long> surgicalHistoryBeginTime) {
        this.surgicalHistoryBeginTime = surgicalHistoryBeginTime;
    }

    public ObservableField<Long> getSurgicalHistoryEndTime() {
        return surgicalHistoryEndTime;
    }

    public void setSurgicalHistoryEndTime(ObservableField<Long> surgicalHistoryEndTime) {
        this.surgicalHistoryEndTime = surgicalHistoryEndTime;
    }

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

    public ObservableField<String> getLeaveHospitalDiagnostics() {
        return leaveHospitalDiagnostics;
    }

    public void setLeaveHospitalDiagnostics(ObservableField<String> leaveHospitalDiagnostics) {
        this.leaveHospitalDiagnostics = leaveHospitalDiagnostics;
    }

    public ObservableField<String> getDoctorSuggestion() {
        return doctorSuggestion;
    }

    public void setDoctorSuggestion(ObservableField<String> doctorSuggestion) {
        this.doctorSuggestion = doctorSuggestion;
    }
}
