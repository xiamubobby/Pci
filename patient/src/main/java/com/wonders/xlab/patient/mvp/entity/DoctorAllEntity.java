package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorAllEntity extends BaseEntity {

    /**
     * dgroupId : 1
     * groupName : 心血管
     * ownerId : 5
     * orderId : 3
     * invalid : 0
     * imgroupId : 166710012339552740
     * description : 瑞金医院
     * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/patient/avatar/1/头像-春子.png"]
     * serviceUrl : null
     * serviceInfos : [{"id":1,"name":"电话"},{"id":2,"name":"视频"}]
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String dgroupId;
        private String groupName;
        private String ownerId;
        private String orderId;
        private String invalid;
        private String imgroupId;
        private String description;
        private Object serviceUrl;
        private List<String> avatars;
        /**
         * id : 1
         * name : 电话
         */

        private List<ServiceInfosEntity> serviceInfos;

        public String getDgroupId() {
            return dgroupId;
        }

        public void setDgroupId(String dgroupId) {
            this.dgroupId = dgroupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getInvalid() {
            return invalid;
        }

        public void setInvalid(String invalid) {
            this.invalid = invalid;
        }

        public String getImgroupId() {
            return imgroupId;
        }

        public void setImgroupId(String imgroupId) {
            this.imgroupId = imgroupId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(Object serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public List<String> getAvatars() {
            return avatars;
        }

        public void setAvatars(List<String> avatars) {
            this.avatars = avatars;
        }

        public List<ServiceInfosEntity> getServiceInfos() {
            return serviceInfos;
        }

        public void setServiceInfos(List<ServiceInfosEntity> serviceInfos) {
            this.serviceInfos = serviceInfos;
        }

        public static class ServiceInfosEntity {
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
