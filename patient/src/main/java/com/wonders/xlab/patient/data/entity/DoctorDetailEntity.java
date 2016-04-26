package com.wonders.xlab.patient.data.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/18.
 * 医生个人详情
 */
public class DoctorDetailEntity extends BaseEntity {

    /**
     * ownerName : 张医生edit11
     * servingPeople : 0
     * belongGroup : [{"doctorGroupId":1,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]}]
     * jobTitle : 医生edit11
     * sPackage : [{"dPackageId":2,"name":"健康报1","iconUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","value":10,"unit":"月","description":"速度快减肥服减肥"}]
     * description : 介绍介绍介t
     * servedPeopleCount : 0
     * department : 儿科
     * hospital : 瑞金医院
     * doctorAvatar : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String ownerName;
        private String servingPeople;
        private String jobTitle;
        private String description;
        private String servedPeopleCount;
        private String department;
        private String hospital;
        private String doctorAvatar;
        /**
         * doctorGroupId : 1
         * name : 心血管
         * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         */

        private List<BelongGroupEntity> belongGroup;
        /**
         * dPackageId : 2
         * name : 健康报1
         * iconUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
         * value : 10.0
         * unit : 月
         * description : 速度快减肥服减肥
         */

        private List<SPackageEntity> sPackage;

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getServingPeople() {
            return servingPeople;
        }

        public void setServingPeople(String servingPeople) {
            this.servingPeople = servingPeople;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServedPeopleCount() {
            return servedPeopleCount;
        }

        public void setServedPeopleCount(String servedPeopleCount) {
            this.servedPeopleCount = servedPeopleCount;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getDoctorAvatar() {
            return doctorAvatar;
        }

        public void setDoctorAvatar(String doctorAvatar) {
            this.doctorAvatar = doctorAvatar;
        }

        public List<BelongGroupEntity> getBelongGroup() {
            return belongGroup;
        }

        public void setBelongGroup(List<BelongGroupEntity> belongGroup) {
            this.belongGroup = belongGroup;
        }

        public List<SPackageEntity> getSPackage() {
            return sPackage;
        }

        public void setSPackage(List<SPackageEntity> sPackage) {
            this.sPackage = sPackage;
        }

        public static class BelongGroupEntity {
            private String doctorGroupId;
            private String ownerId;
            private String name;
            private List<String> avatars;

            public String getDoctorGroupId() {
                return doctorGroupId;
            }

            public void setDoctorGroupId(String doctorGroupId) {
                this.doctorGroupId = doctorGroupId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getAvatars() {
                return avatars;
            }

            public void setAvatars(List<String> avatars) {
                this.avatars = avatars;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }
        }

        public static class SPackageEntity {
            private String dPackageId;
            private String name;
            private String iconUrl;
            private String value;
            private String unit;
            /**
             * 0 未购买 1 购买 2过期
             */
            private int orderStatus;
            private String description;

            public String getDPackageId() {
                return dPackageId;
            }

            public void setDPackageId(String dPackageId) {
                this.dPackageId = dPackageId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }
        }
    }
}
