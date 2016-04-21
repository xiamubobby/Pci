package com.wonders.xlab.pci.doctor.module.chatroom.chat.otto;

/**
 * Created by hua on 16/4/4.
 * 更新聊天界面，将一条消息插入聊天列表
 */
public class ChatRoomRecordInsertOtto {
    private String groupId;
    private String groupName;
    private String imGroupId ;
    private String txtContent;
    private String fromWhoAvatarUrl;
    private String fromWhoName;
    private long messageTime;

    public ChatRoomRecordInsertOtto(String groupId, String groupName, String imGroupId, String txtContent, String fromWhoAvatarUrl, String fromWhoName, long messageTime) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.imGroupId = imGroupId;
        this.txtContent = txtContent;
        this.fromWhoAvatarUrl = fromWhoAvatarUrl;
        this.fromWhoName = fromWhoName;
        this.messageTime = messageTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getFromWhoAvatarUrl() {
        return fromWhoAvatarUrl;
    }

    public void setFromWhoAvatarUrl(String fromWhoAvatarUrl) {
        this.fromWhoAvatarUrl = fromWhoAvatarUrl;
    }

    public String getFromWhoName() {
        return fromWhoName;
    }

    public void setFromWhoName(String fromWhoName) {
        this.fromWhoName = fromWhoName;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
