package com.wonders.xlab.pci.doctor.module.me.groupmanage.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

/**
 * Created by hua on 16/4/7.
 */
public class GroupManageBean extends BaseObservable{
    private String groupName;
    private String ownerName;
    private String ownerHospital;
    private String ownerDepartment;
    private String ownerTitle;
    private List<ServiceIconBean> serviceIconList;
    private int servingPeopleCounts;
    private int servedPeopleCounts;
    private long createTimeInMill;
    private List<String> avatarList;
    private int groupMemberCounts;
    private boolean isBelongToCurrentDoctor;

    @Bindable
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Bindable
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Bindable
    public String getOwnerHospital() {
        return ownerHospital;
    }

    public void setOwnerHospital(String ownerHospital) {
        this.ownerHospital = ownerHospital;
    }

    @Bindable
    public String getOwnerDepartment() {
        return ownerDepartment;
    }

    public void setOwnerDepartment(String ownerDepartment) {
        this.ownerDepartment = ownerDepartment;
    }

    @Bindable
    public String getOwnerTitle() {
        return ownerTitle;
    }

    public void setOwnerTitle(String ownerTitle) {
        this.ownerTitle = ownerTitle;
    }

    public List<ServiceIconBean> getServiceIconList() {
        return serviceIconList;
    }

    public void setServiceIconList(List<ServiceIconBean> serviceIconList) {
        this.serviceIconList = serviceIconList;
    }

    @Bindable
    public int getServingPeopleCounts() {
        return servingPeopleCounts;
    }

    public void setServingPeopleCounts(int servingPeopleCounts) {
        this.servingPeopleCounts = servingPeopleCounts;
    }

    @Bindable
    public int getServedPeopleCounts() {
        return servedPeopleCounts;
    }

    public void setServedPeopleCounts(int servedPeopleCounts) {
        this.servedPeopleCounts = servedPeopleCounts;
    }

    @Bindable
    public long getCreateTimeInMill() {
        return createTimeInMill;
    }

    public void setCreateTimeInMill(long createTimeInMill) {
        this.createTimeInMill = createTimeInMill;
    }

    public List<String> getAvatarList() {
        return avatarList;
    }

    public void setAvatarList(List<String> avatarList) {
        this.avatarList = avatarList;
    }

    @Bindable
    public int getGroupMemberCounts() {
        return groupMemberCounts;
    }

    public void setGroupMemberCounts(int groupMemberCounts) {
        this.groupMemberCounts = groupMemberCounts;
    }

    @Bindable
    public boolean isBelongToCurrentDoctor() {
        return isBelongToCurrentDoctor;
    }

    public void setBelongToCurrentDoctor(boolean belongToCurrentDoctor) {
        isBelongToCurrentDoctor = belongToCurrentDoctor;
    }
}
