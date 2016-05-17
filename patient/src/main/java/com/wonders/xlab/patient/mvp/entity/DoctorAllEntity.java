package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BasePageEntity;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorAllEntity extends BasePageEntity<DoctorAllEntity.ResultEntity> {
    public static class ResultEntity {
        private String doctorGroupId;
        private String groupName;
        private String ownerId;
        private String orderId;
        private String orderStatus;
        private String statusColor;
        private String imgroupId;
        private String ownerName;
        private boolean multi;
        private String department;
        private String jobTitle;
        private String hospitalName;
        private List<String> avatars;
        private List<String> packageUrls;

        public String getDoctorGroupId() {
            return doctorGroupId;
        }

        public void setDoctorGroupId(String doctorGroupId) {
            this.doctorGroupId = doctorGroupId;
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

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getStatusColor() {
            return statusColor;
        }

        public void setStatusColor(String statusColor) {
            this.statusColor = statusColor;
        }

        public String getImgroupId() {
            return imgroupId;
        }

        public void setImgroupId(String imgroupId) {
            this.imgroupId = imgroupId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public boolean isMulti() {
            return multi;
        }

        public void setMulti(boolean multi) {
            this.multi = multi;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public List<String> getAvatars() {
            return avatars;
        }

        public void setAvatars(List<String> avatars) {
            this.avatars = avatars;
        }

        public List<String> getPackageUrls() {
            return packageUrls;
        }

        public void setPackageUrls(List<String> packageUrls) {
            this.packageUrls = packageUrls;
        }
    }
}
