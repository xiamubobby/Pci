package com.wonders.xlab.patient.module.healthrecord.prescription.adapter.bean;

import java.util.List;

/**
 * Created by hua on 16/4/26.
 */
public class PrescriptionBean {

    public String recordTime;
    private String hospitalName;
    public List<String> medicineList;

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<String> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<String> medicineList) {
        this.medicineList = medicineList;
    }
}