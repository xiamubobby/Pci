package com.wonders.xlab.patient.mvp.entity;

/**
 * Created by hua on 16/5/11.
 */
public class MedicationUsagesEntity {
    private String medicationName;
    private String medicationNum;
    private String pharmaceuticalUnit;

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationNum() {
        return medicationNum;
    }

    public void setMedicationNum(String medicationNum) {
        this.medicationNum = medicationNum;
    }

    public String getPharmaceuticalUnit() {
        return pharmaceuticalUnit;
    }

    public void setPharmaceuticalUnit(String pharmaceuticalUnit) {
        this.pharmaceuticalUnit = pharmaceuticalUnit;
    }
}
