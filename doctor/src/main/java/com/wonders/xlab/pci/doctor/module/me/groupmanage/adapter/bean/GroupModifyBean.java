package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean;

import java.util.List;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyBean {
    private String groupId;
    private List<GroupModifyMemberBean> mMemberInfoList;
    private String groupName;
    private String groupDesc;
    private List<String> publishedServiceIconList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<GroupModifyMemberBean> getMemberInfoList() {
        return mMemberInfoList;
    }

    public void setMemberInfoList(List<GroupModifyMemberBean> memberInfoList) {
        mMemberInfoList = memberInfoList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public List<String> getPublishedServiceIconList() {
        return publishedServiceIconList;
    }

    public void setPublishedServiceIconList(List<String> publishedServiceIconList) {
        this.publishedServiceIconList = publishedServiceIconList;
    }
}
