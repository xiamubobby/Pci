package com.wonders.xlab.pci.doctor.mvp.entity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageCreateBody {

    /**
     * doctorPackageId : 1
     * publishType : Publish
     * doctorGroupId : 1
     * servicePackageId : 1
     * price : 200
     * cycleNumber : 30
     */

    private int doctorPackageId;
    private String publishType;
    private int doctorGroupId;
    private int servicePackageId;
    private int price;
    private String cycleNumber;

    public int getDoctorPackageId() {
        return doctorPackageId;
    }

    public void setDoctorPackageId(int doctorPackageId) {
        this.doctorPackageId = doctorPackageId;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public int getDoctorGroupId() {
        return doctorGroupId;
    }

    public void setDoctorGroupId(int doctorGroupId) {
        this.doctorGroupId = doctorGroupId;
    }

    public int getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(String cycleNumber) {
        this.cycleNumber = cycleNumber;
    }
}
