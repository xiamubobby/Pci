package com.wonders.xlab.patient.mvp.entity;

/**
 * Created by hua on 16/5/10.
 * 药品
 */
public class MedicineEntity {
    /**
     * id : 28
     * name : 华法林钠片（华法令）
     * company : 信谊
     * dosageForm : 片剂
     * doseSpecification : 2.5mg*30片*2板/盒
     */
    private String id;
    private String name;
    private String company;
    private String dosageForm;
    private String doseSpecification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getDoseSpecification() {
        return doseSpecification;
    }

    public void setDoseSpecification(String doseSpecification) {
        this.doseSpecification = doseSpecification;
    }
}
