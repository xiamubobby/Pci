package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiGroupInviteEntity extends BaseEntity {

    /**
     * type : 4
     * groupId : 10
     * groupName : 超妹的个人诊所
     * groupDescription : 1111111
     * ownerName : 超妹
     * ownerHos : 第一人民医院
     * ownerDepartment : 儿科
     * ownerJobTitle : 主任医师
     * avatarUrls : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
     * isAgree : true
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int type;
        private String groupId;
        private String groupName;
        private String groupDescription;
        private String ownerId;
        private String ownerName;
        private String ownerHos;
        private String ownerDepartment;
        private String ownerJobTitle;
        private boolean isAgree;
        private long groupCreateTime;
        private List<String> avatarUrls;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getGroupDescription() {
            return groupDescription;
        }

        public void setGroupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerHos() {
            return ownerHos;
        }

        public void setOwnerHos(String ownerHos) {
            this.ownerHos = ownerHos;
        }

        public String getOwnerDepartment() {
            return ownerDepartment;
        }

        public void setOwnerDepartment(String ownerDepartment) {
            this.ownerDepartment = ownerDepartment;
        }

        public String getOwnerJobTitle() {
            return ownerJobTitle;
        }

        public void setOwnerJobTitle(String ownerJobTitle) {
            this.ownerJobTitle = ownerJobTitle;
        }

        public boolean isIsAgree() {
            return isAgree;
        }

        public void setIsAgree(boolean isAgree) {
            this.isAgree = isAgree;
        }

        public List<String> getAvatarUrls() {
            return avatarUrls;
        }

        public void setAvatarUrls(List<String> avatarUrls) {
            this.avatarUrls = avatarUrls;
        }

        public long getGroupCreateTime() {
            return groupCreateTime;
        }

        public void setGroupCreateTime(long groupCreateTime) {
            this.groupCreateTime = groupCreateTime;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }
    }
}
