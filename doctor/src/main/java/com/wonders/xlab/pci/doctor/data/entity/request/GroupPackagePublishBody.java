package com.wonders.xlab.pci.doctor.data.entity.request;

/**
 * Created by hua on 16/4/15.
 */
public class GroupPackagePublishBody {

    /**
     * doctorPackageId : 1
     * publishType : Publish
     * ownerId : 1
     * servicePackageId : 1
     * numberValue : 200
     */

    private String doctorId;
    private String doctorPackageId;
    private String publishType;
    private String ownerId;
    private String servicePackageId;
    private int numberValue;

    public String getDoctorPackageId() {
        return doctorPackageId;
    }

    public void setDoctorPackageId(String doctorPackageId) {
        this.doctorPackageId = doctorPackageId;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(String servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
