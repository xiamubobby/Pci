package com.wonders.xlab.patient.module.mydoctor.adapter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;

import com.wonders.xlab.patient.BR;

import java.util.List;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorItemBean extends BaseObservable{
    public final static long TYPE_IN_SERVICE = 0;
    public final static long TYPE_OUT_OF_SERVICE = 1;

    /**
     * 医生小组id
     */
    private String groupId;
    private String ownerId;
    /**
     * 环信id
     */
    private String imGroupId;
    /**
     * 因为这个type要用与{@link RecyclerView.Adapter}的分类HeaderId，所以必须要定义成long
     *
     * {@link #TYPE_IN_SERVICE}：正在服务
     * {@link #TYPE_OUT_OF_SERVICE}：历史记录
     */
    private long type;
    private List<String> portraitUrl;
    private String doctorGroupName;
    /**
     * 最新一条聊天记录时间
     */
    private String timeStr;
    /**
     * 最新一条聊天记录
     */
    private String latestChatMessage;

    public List<String> getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(List<String> portraitUrl) {
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
