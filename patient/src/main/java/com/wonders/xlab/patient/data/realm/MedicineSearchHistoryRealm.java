package com.wonders.xlab.patient.data.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/5/10.
 */
public class MedicineSearchHistoryRealm extends RealmObject {
    private String id;
    /**
     * 药品名
     */
    private String medicineName;
    /**
     * 厂家名
     */
    private String companyName;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 剂型
     */
    private String formOfDrug;

    /**
     * 剂量
     */
    private String dose;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getFormOfDrug() {
        return formOfDrug;
    }

    public void setFormOfDrug(String formOfDrug) {
        this.formOfDrug = formOfDrug;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
