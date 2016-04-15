package com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.bean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hua on 16/4/10.
 */
public class PackageInfoBean {
    private String doctorPackageId;
    private String unitTitle;
    private String unit;
    private List<HashMap<String,String>> defaultValues;
    private String descTitle;
    private String desc;
    private int defaultSpPosition;

    public PackageInfoBean(String doctorPackageId, String unitTitle, String unit, List<HashMap<String, String>> defaultValues, String descTitle, String desc, int defaultSpPosition) {
        this.doctorPackageId = doctorPackageId;
        this.unitTitle = unitTitle;
        this.unit = unit;
        this.defaultValues = defaultValues;
        this.descTitle = descTitle;
        this.desc = desc;
        this.defaultSpPosition = defaultSpPosition;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<HashMap<String,String>> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(List<HashMap<String,String>> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public String getDescTitle() {
        return descTitle;
    }

    public void setDescTitle(String descTitle) {
        this.descTitle = descTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDoctorPackageId() {
        return doctorPackageId;
    }

    public void setDoctorPackageId(String doctorPackageId) {
        this.doctorPackageId = doctorPackageId;
    }

    public int getDefaultSpPosition() {
        return defaultSpPosition;
    }

    public void setDefaultSpPosition(int defaultSpPosition) {
        this.defaultSpPosition = defaultSpPosition;
    }
}
