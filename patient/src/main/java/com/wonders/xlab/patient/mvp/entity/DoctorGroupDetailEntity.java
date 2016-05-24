package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/17.
 * 医生小组详情（包括个人和多人）
 */
public class DoctorGroupDetailEntity extends BaseEntity {


    /**
     * servingPeople : 0
     * groupAvatar : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
     * members : [{"doctorId":6,"avatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","name":"张医生edit11","jobTitle":"医生edit11"},{"doctorId":5,"avatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","name":"张医生edit11","jobTitle":"医生edit11"}]
     * jobTitle : 医生edit11
     * sPackage : [{"dPackageId":1,"name":"健康报1","iconUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","value":10,"unit":"月","description":"速度快减肥服减肥"}]
     * description : 阿里肯德基放辣椒的
     * servedPeopleCount : 0
     * department : 儿科
     * hospital : 瑞金医院
     * multi : true
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
        private String jobTitle;
        private String description;
        private String servingPeople;
        private String servedPeopleCount;
        private String department;
        private String hospital;
        private boolean multi;
        private List<String> groupAvatar;
        /**
         * doctorId : 6
         * avatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
         * name : 张医生edit11
         * jobTitle : 医生edit11
         */

        private List<MembersEntity> members;

        /**
         * name : 心血管
         * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         */

        private List<BelongGroupEntity> belongGroup;

        public List<BelongGroupEntity> getBelongGroup() {
            return belongGroup;
        }

        public void setBelongGroup(List<BelongGroupEntity> belongGroup) {
            this.belongGroup = belongGroup;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public static class BelongGroupEntity {
            private String name;
            private String doctorGroupId;
            private String ownerId;
            private List<String> avatars;

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

            public String getDoctorGroupId() {
                return doctorGroupId;
            }

            public void setDoctorGroupId(String doctorGroupId) {
                this.doctorGroupId = doctorGroupId;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }
        }

        /**
         * dPackageId : 1
         * name : 健康报1
         * iconUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
         * value : 10.0
         * unit : 月
         * description : 速度快减肥服减肥
         */

        private List<SPackageEntity> sPackage;

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

        public boolean isMulti() {
            return multi;
        }

        public void setMulti(boolean multi) {
            this.multi = multi;
        }

        public List<String> getGroupAvatar() {
            return groupAvatar;
        }

        public void setGroupAvatar(List<String> groupAvatar) {
            this.groupAvatar = groupAvatar;
        }

        public List<MembersEntity> getMembers() {
            return members;
        }

        public void setMembers(List<MembersEntity> members) {
            this.members = members;
        }

        public List<SPackageEntity> getSPackage() {
            return sPackage;
        }

        public void setSPackage(List<SPackageEntity> sPackage) {
            this.sPackage = sPackage;
        }

        public static class MembersEntity {
            private String doctorId;
            private String avatarUrl;
            private String name;
            private String jobTitle;

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
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
        }

        public static class SPackageEntity {
            private String dPackageId;
            private String name;
            private String iconUrl;
            private String value;
            private String unit;
            private int price;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
