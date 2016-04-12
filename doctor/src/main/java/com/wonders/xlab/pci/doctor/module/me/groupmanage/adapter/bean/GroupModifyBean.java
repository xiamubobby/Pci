package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyBean {
    private List<GroupModifyMemberBean> mMemberInfoList;
    private String groupName;
    private String groupDesc;
    private List<String> publishedServiceIconList;
    /**
     * Owner
     * Manager
     * Member
     */
    private String managerType;
    private boolean canGrant;

    public boolean isAdmin() {
        return !TextUtils.isEmpty(managerType) && !managerType.toLowerCase().equals("member");
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

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public boolean isCanGrant() {
        return canGrant;
    }

    public void setCanGrant(boolean canGrant) {
        this.canGrant = canGrant;
    }
}
