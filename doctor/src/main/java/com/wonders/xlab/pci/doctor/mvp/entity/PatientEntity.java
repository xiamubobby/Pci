package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/19.
 */
public class PatientEntity extends BaseEntity {

    /**
     * id : 1
     * name : 心血管
     * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/patient/avatar/1
     * symptom : 稳定心绞痛
     * age : 19
     * gender : 女
     * lastOperationTime : 术后1个月
     * doctorGroupId : 1
     * groupName : “”
     */

    private List<RetValuesEntity> ret_values;

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String id;
        private String name;
        private String avatarUrl;
        private String symptom;
        private String age;
        private String tel;
        private String gender;
        private String lastOperationTime;
        private String ownerId;
        private String groupName;
        private String imGroupId;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public void setSymptom(String symptom) {
            this.symptom = symptom;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setLastOperationTime(String lastOperationTime) {
            this.lastOperationTime = lastOperationTime;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getSymptom() {
            return symptom;
        }

        public String getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getLastOperationTime() {
            return lastOperationTime;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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
}
