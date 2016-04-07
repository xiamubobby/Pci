package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyMemberBean {
    public final static int TYPE_MEMBER = 0;
    public final static int TYPE_ADD = 1;
    public final static int TYPE_MINUS = 2;

    private int type = TYPE_MEMBER;
    private String memberId;
    private String avatarUrl;
    private String memberName;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
