package com.wonders.xlab.patient.module.main.doctors.adapter.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.patient.BR;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorItemBean extends BaseObservable{
    public final static long TYPE_IN_SERVICE = 0;
    public final static long TYPE_OUT_OF_SERVICE = 1;

    private String groupId;
    private String imGroupId;
    /**
     * {@link TYPE_IN_SERVICE}：正在服务
     * {@link TYPE_OUT_OF_SERVICE}：历史记录
     */
    private long type;
    private String portraitUrl;
    private String doctorGroupName;
    private String timeStr;
    private String latestChatMessage;

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
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
    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
        notifyPropertyChanged(BR.timeStr);
    }

    @Bindable
    public String getLatestChatMessage() {
        return latestChatMessage;
    }

    public void setLatestChatMessage(String latestChatMessage) {
        this.latestChatMessage = latestChatMessage;
        notifyPropertyChanged(BR.latestChatMessage);
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

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
}
