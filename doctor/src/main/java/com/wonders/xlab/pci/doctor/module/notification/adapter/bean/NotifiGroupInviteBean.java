package com.wonders.xlab.pci.doctor.module.notification.adapter.bean;

import java.util.List;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInviteBean {
    private String ownerId;
    private long recordTime;
    private String groupName;
    private String groupMemberCounts;
    private String ownerName;
    private String ownerJobTitle;
    private String ownerDepartment;
    private String ownerHospital;
    private String groupDesc;
    private List<String> avatarUrls;
    /**
     * 0：未操作
     * 1：已同意
     * 2：已拒绝
     */
    private int status = 0;

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupMemberCounts() {
        return groupMemberCounts;
    }

    public void setGroupMemberCounts(String groupMemberCounts) {
        this.groupMemberCounts = groupMemberCounts;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerJobTitle() {
        return ownerJobTitle;
    }

    public void setOwnerJobTitle(String ownerJobTitle) {
        this.ownerJobTitle = ownerJobTitle;
    }

    public String getOwnerDepartment() {
        return ownerDepartment;
    }

    public void setOwnerDepartment(String ownerDepartment) {
        this.ownerDepartment = ownerDepartment;
    }

    public String getOwnerHospital() {
        return ownerHospital;
    }

    public void setOwnerHospital(String ownerHospital) {
        this.ownerHospital = ownerHospital;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(List<String> avatarUrls) {
        this.avatarUrls = avatarUrls;
    }
}
