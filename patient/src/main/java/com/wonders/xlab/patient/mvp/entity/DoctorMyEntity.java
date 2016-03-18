package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorMyEntity extends BaseEntity {

    /**
     * serviceFalse : {"size":20,"page":0,"content":[{"doctorGroupId":1,"imGroupId":166710012339552740,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"content":"可能","timeExp":"15天前"},{"doctorGroupId":1,"imGroupId":166710012339552740,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"content":"可能","timeExp":"15天前"}]}
     * serviceTrue : [{"doctorGroupId":1,"imGroupId":166710012339552740,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"content":"可能","timeExp":"2016-03-03 13:52"}]
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        /**
         * size : 20
         * page : 0
         * content : [{"doctorGroupId":1,"imGroupId":166710012339552740,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"content":"可能","timeExp":"15天前"},{"doctorGroupId":1,"imGroupId":166710012339552740,"name":"心血管","avatars":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"content":"可能","timeExp":"15天前"}]
         */

        private ServiceFalseEntity serviceFalse;
        /**
         * doctorGroupId : 1
         * imGroupId : 166710012339552740
         * name : 心血管
         * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         * content : 可能
         * timeExp : 2016-03-03 13:52
         */

        private List<ServiceTrueEntity> serviceTrue;

        public ServiceFalseEntity getServiceFalse() {
            return serviceFalse;
        }

        public void setServiceFalse(ServiceFalseEntity serviceFalse) {
            this.serviceFalse = serviceFalse;
        }

        public List<ServiceTrueEntity> getServiceTrue() {
            return serviceTrue;
        }

        public void setServiceTrue(List<ServiceTrueEntity> serviceTrue) {
            this.serviceTrue = serviceTrue;
        }

        public static class ServiceFalseEntity {
            private String size;
            private String page;
            /**
             * doctorGroupId : 1
             * imGroupId : 166710012339552740
             * name : 心血管
             * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
             * content : 可能
             * timeExp : 15天前
             */

            private List<ContentEntity> content;

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getPage() {
                return page;
            }

            public void setPage(String page) {
                this.page = page;
            }

            public List<ContentEntity> getContent() {
                return content;
            }

            public void setContent(List<ContentEntity> content) {
                this.content = content;
            }

            public static class ContentEntity {
                private String doctorGroupId;
                private String imGroupId;
                private String name;
                private String content;
                private String timeExp;
                private List<String> avatars;

                public String getDoctorGroupId() {
                    return doctorGroupId;
                }

                public void setDoctorGroupId(String doctorGroupId) {
                    this.doctorGroupId = doctorGroupId;
                }

                public String getImGroupId() {
                    return imGroupId;
                }

                public void setImGroupId(String imGroupId) {
                    this.imGroupId = imGroupId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getTimeExp() {
                    return timeExp;
                }

                public void setTimeExp(String timeExp) {
                    this.timeExp = timeExp;
                }

                public List<String> getAvatars() {
                    return avatars;
                }

                public void setAvatars(List<String> avatars) {
                    this.avatars = avatars;
                }
            }
        }

        public static class ServiceTrueEntity {
            private String doctorGroupId;
            private String imGroupId;
            private String name;
            private String content;
            private String timeExp;
            private List<String> avatars;

            public String getDoctorGroupId() {
                return doctorGroupId;
            }

            public void setDoctorGroupId(String doctorGroupId) {
                this.doctorGroupId = doctorGroupId;
            }

            public String getImGroupId() {
                return imGroupId;
            }

            public void setImGroupId(String imGroupId) {
                this.imGroupId = imGroupId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTimeExp() {
                return timeExp;
            }

            public void setTimeExp(String timeExp) {
                this.timeExp = timeExp;
            }

            public List<String> getAvatars() {
                return avatars;
            }

            public void setAvatars(List<String> avatars) {
                this.avatars = avatars;
            }
        }
    }
}
