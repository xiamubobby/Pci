package com.wonders.xlab.pci.doctor.mvp.entity;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class LoginEntity extends BaseEntity {

    /**
     * id : 5
     * tel : 13621673988
     * name : 刘二
     * jobTitle : 副主任医师
     * type : Attending
     * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * description : 医生简介
     * hospital : {"id":2,"name":"瑞金医院"}
     * department : {"id":1,"name":"儿科"}
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String id;
        private String tel;
        private String name;
        private String jobTitle;
        private String type;
        private String avatarUrl;
        private String description;
        /**
         * id : 2
         * name : 瑞金医院
         */

        private HospitalEntity hospital;
        /**
         * id : 1
         * name : 儿科
         */

        private DepartmentEntity department;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public HospitalEntity getHospital() {
            return hospital;
        }

        public void setHospital(HospitalEntity hospital) {
            this.hospital = hospital;
        }

        public DepartmentEntity getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentEntity department) {
            this.department = department;
        }

        public static class HospitalEntity {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class DepartmentEntity {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
