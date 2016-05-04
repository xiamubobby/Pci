package com.wonders.xlab.pci.doctor.module.groupmanage.adapter.bean;

import java.util.List;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyBean {
    private List<GroupModifyMemberBean> mMemberInfoList;
    private String groupName;
    private String groupDesc;
    private List<String> publishedServiceIconList;
    private boolean canGrant;

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

    public boolean isCanGrant() {
        return canGrant;
    }

    public void setCanGrant(boolean canGrant) {
        this.canGrant = canGrant;
    }
}
