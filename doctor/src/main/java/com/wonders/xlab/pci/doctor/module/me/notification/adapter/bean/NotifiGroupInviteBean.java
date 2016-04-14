package com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInviteBean {
    private String id;
    private long recordTime;
    private String groupName;
    private String groupMemberCounts;
    private String ownerName;
    private String ownerAvatarUrl;
    private String ownerJobTitle;
    private String ownerDepartment;
    private String ownerHospital;
    private String groupDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    public void setOwnerAvatarUrl(String ownerAvatarUrl) {
        this.ownerAvatarUrl = ownerAvatarUrl;
    }
}
