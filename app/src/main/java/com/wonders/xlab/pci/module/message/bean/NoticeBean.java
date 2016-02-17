package com.wonders.xlab.pci.module.message.bean;


import com.wonders.xlab.pci.realm.ChatRealmEntity;

/**
 * Created by hua on 15/12/14.
 */
public class NoticeBean extends ChatBean {
    private String portrait;
    private String name;
    private String title;
    private long updateTime;

    public void setMessageRealmEntity(ChatRealmEntity realmEntity) {
        setPortrait(realmEntity.getPortrait());
        setName(realmEntity.getName());
        setTitle(realmEntity.getTitle());
        setUpdateTime(realmEntity.getUpdateTime());
    }

    @Override
    public int getItemLayout() {
        return ChatBean.ITEM_TODAY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
