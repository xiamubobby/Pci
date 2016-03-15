package com.wonders.xlab.patient.module.main.doctors.adapter.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.patient.BR;

import java.util.List;

/**
 * Created by hua on 16/3/14.
 */
public class AllDoctorItemBean extends BaseObservable {
    public final static long HEADER_ID_IN_SERVICE = 0;
    public final static long HEADER_ID_OUT_OF_SERVICE = 1;

    /**
     * 是否为个人医生
     */
    private boolean isPersonal;

    private String portraitUrl;
    /**
     * 医生小组名称
     */
    private String doctorGroupName;
    /**
     * 负责人姓名
     */
    private String adminName;
    /**
     * 科室
     */
    private String department;
    /**
     * 职称
     */
    private String title;
    /**
     * 所在医院
     */
    private String hospital;

    /**
     * 已购买，已过期，主治医生，手术医院等
     */
    private String tagStr;

    private List<String> serviceIconUrl;

    @Bindable
    public static long getHeaderIdInService() {
        return HEADER_ID_IN_SERVICE;
    }

    @Bindable
    public static long getHeaderIdOutOfService() {
        return HEADER_ID_OUT_OF_SERVICE;
    }

    @Bindable
    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
        notifyPropertyChanged(BR.portraitUrl);
    }

    @Bindable
    public String getDoctorGroupName() {
        return doctorGroupName;
    }

    public void setDoctorGroupName(String doctorGroupName) {
        this.doctorGroupName = doctorGroupName;
        notifyPropertyChanged(BR.doctorGroupName);
    }

    @Bindable
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
        notifyPropertyChanged(BR.adminName);
    }

    @Bindable
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
        notifyPropertyChanged(BR.department);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
        notifyPropertyChanged(BR.hospital);
    }

    @Bindable
    public String getTagStr() {
        return tagStr;
    }

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
        notifyPropertyChanged(BR.tagStr);
    }

    @Bindable
    public List<String> getServiceIconUrl() {
        return serviceIconUrl;
    }

    public void setServiceIconUrl(List<String> serviceIconUrl) {
        this.serviceIconUrl = serviceIconUrl;
        notifyPropertyChanged(BR.serviceIconUrl);
    }

    @Bindable
    public boolean isPersonal() {
        return isPersonal;
    }

    public void setPersonal(boolean personal) {
        isPersonal = personal;
        notifyPropertyChanged(BR.personal);
    }
}
