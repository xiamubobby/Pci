package com.wonders.xlab.patient.realm;

import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;

import io.realm.RealmObject;

/**
 * Created by hua on 16/3/27.
 */
public class MyDoctorRealm extends RealmObject {
    private String patientId;
    private boolean hasRead;
    /**
     * 医生小组id
     */
    private String groupId;
    /**
     * 环信id
     */
    private String imGroupId;
    /**
     * {@link MyDoctorItemBean#TYPE_IN_SERVICE}：正在服务
     * {@link MyDoctorItemBean#TYPE_OUT_OF_SERVICE}：历史记录
     */
    private long type;
    private String portraitUrl;
    private String doctorGroupName;
    /**
     * 最新一条聊天记录时间
     */
    private String timeStr;
    /**
     * 最新一条聊天记录
     */
    private String latestChatMessage;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getDoctorGroupName() {
        return doctorGroupName;
    }

    public void setDoctorGroupName(String doctorGroupName) {
        this.doctorGroupName = doctorGroupName;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getLatestChatMessage() {
        return latestChatMessage;
    }

    public void setLatestChatMessage(String latestChatMessage) {
        this.latestChatMessage = latestChatMessage;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }
}
