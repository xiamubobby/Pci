package com.wonders.xlab.pci.doctor.mvp.entity;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class LoginEntity extends BaseEntity {

    /**
     * id : 5
     * tel : 13621673988
     * password : 96e79218965eb72c92a549dd5a330112
     * name : ( ⊙o⊙ )?
     * hospital : (ˇˍˇ)
     * department : ︿(￣︶￣)︿
     * jobTitle : (⊙o⊙)…
     * type : OnlineDoctor
     * avatarUrl : null
     * description : null
     * groups : null
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private String id;
        private String tel;
        private String password;
        private String name;
        private String hospital;
        private String department;
        private String jobTitle;
        private String type;
        private String avatarUrl;
        private String description;
        private String groups;

        public void setId(String id) {
            this.id = id;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setGroups(String groups) {
            this.groups = groups;
        }

        public String getId() {
            return id;
        }

        public String getTel() {
            return tel;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getHospital() {
            return hospital;
        }

        public String getDepartment() {
            return department;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public String getType() {
            return type;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getGroups() {
            return groups;
        }
    }
}
