package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupDetailEntity extends BaseEntity {

    /**
     * managerType : Member
     * groupName : id为6的诊所
     * groupDescription : id为6的诊所阿萨德发生的发生的
     * serviceUrls : []
     * members : [{"doctorName":"张三","doctorId":6,"doctorAvatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"},{"doctorName":"超妹","doctorId":7,"doctorAvatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"},{"doctorName":"刘二","doctorId":5,"doctorAvatarUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"}]
     * canGrant : true
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String managerType;
        private String groupName;
        private String groupDescription;
        private boolean canGrant;
        private List<String> serviceUrls;
        /**
         * doctorName : 张三
         * doctorId : 6
         * doctorAvatarUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg
         */

        private List<MembersEntity> members;

        public String getManagerType() {
            return managerType;
        }

        public void setManagerType(String managerType) {
            this.managerType = managerType;
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

        public boolean isCanGrant() {
            return canGrant;
        }

        public void setCanGrant(boolean canGrant) {
            this.canGrant = canGrant;
        }

        public List<String> getServiceUrls() {
            return serviceUrls;
        }

        public void setServiceUrls(List<String> serviceUrls) {
            this.serviceUrls = serviceUrls;
        }

        public List<MembersEntity> getMembers() {
            return members;
        }

        public void setMembers(List<MembersEntity> members) {
            this.members = members;
        }

        public static class MembersEntity {
            private String doctorName;
            private String doctorId;
            private String doctorImId;
            private String doctorAvatarUrl;
            private boolean isAgree;

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getDoctorAvatarUrl() {
                return doctorAvatarUrl;
            }

            public void setDoctorAvatarUrl(String doctorAvatarUrl) {
                this.doctorAvatarUrl = doctorAvatarUrl;
            }

            public String getDoctorImId() {
                return doctorImId;
            }

            public void setDoctorImId(String doctorImId) {
                this.doctorImId = doctorImId;
            }

            public boolean isAgree() {
                return isAgree;
            }

            public void setAgree(boolean agree) {
                isAgree = agree;
            }
        }
    }
}
