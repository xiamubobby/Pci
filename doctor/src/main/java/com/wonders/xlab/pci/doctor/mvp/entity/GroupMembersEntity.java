package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/13.
 * 组内成员，不包括自己和创建者
 */
public class GroupMembersEntity extends BaseEntity {

    /**
     * id : 6
     * tel : 13248227958
     * name : 张三
     * jobTitle : 主任医师
     * type : Attending
     * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     * description : 医生简介
     * hospital : {"id":2,"name":"瑞金医院"}
     * department : {"id":1,"name":"儿科"}
     * imId : doctor13248227958
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
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
        private String imId;

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

        public String getImId() {
            return imId;
        }

        public void setImId(String imId) {
            this.imId = imId;
        }

        public static class HospitalEntity {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
