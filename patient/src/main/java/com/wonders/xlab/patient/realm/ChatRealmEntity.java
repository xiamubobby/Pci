package com.wonders.xlab.patient.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/2/17.
 */
public class ChatRealmEntity extends RealmObject {
    /**
     * 当前登录用户id
     */
    private String userId;
    /**
     * 医生头像
     */
    private String portrait;
    /**
     * 医生姓名
     */
    private String name;
    /**
     * 消息标题
     */
    private String title;
    private String content;
    /**
     * 记录时间
     */
    private long updateTime;
    /**
     * true：与医生的历史聊天记录
     * false：后台推送的提醒
     */
    private boolean isMessage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isMessage() {
        return isMessage;
    }

    public void setMessage(boolean message) {
        isMessage = message;
    }
}
