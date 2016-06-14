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
            /**
             * 数据库总条数
             */
            private int totalElements;
            /**
             * 是否是最后一页
             */
            private boolean last;
            /**
             * 总共的页数
             */
            private int totalPages;
            /**
             * 一页的大小
             */
            private int size;
            /**
             * 当前为第几页
             */
            private int number;
            private Object sort;
            /**
             * 是否为第一页
             */
            private boolean first;
            /**
             * 当前页返回的条数
             */
            private int numberOfElements;
            /**
             * doctorGroupId : 1
             * imGroupId : 166710012339552740
             * name : 心血管
             * avatars : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
             * content : 可能
             * timeExp : 15天前
             */

            private List<ContentEntity> content;

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotalElements() {
                return totalElements;
            }

            public void setTotalElements(int totalElements) {
                this.totalElements = totalElements;
            }

            public boolean isLast() {
                return last;
            }

            public void setLast(boolean last) {
                this.last = last;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public boolean isFirst() {
                return first;
            }

            public void setFirst(boolean first) {
                this.first = first;
            }

            public int getNumberOfElements() {
                return numberOfElements;
            }

            public void setNumberOfElements(int numberOfElements) {
                this.numberOfElements = numberOfElements;
            }

            public List<ContentEntity> getContent() {
                return content;
            }

            public void setContent(List<ContentEntity> content) {
                this.content = content;
            }

            public static class ContentEntity {
                private String ownerId;
                private String imGroupId;
                private String hospitalName;
                private long expirationTime;
                private String name;
                private String content;
                private String timeExp;
                private List<String> avatars;

                public String getImGroupId() {
                    return imGroupId;
                }

                public void setImGroupId(String imGroupId) {
                    this.imGroupId = imGroupId;
                }

                public String getHospitalName() {
                    return hospitalName;
                }

                public void setHospitalName(String hospitalName) {
                    this.hospitalName = hospitalName;
                }

                public long getExpirationTime() {
                    return expirationTime;
                }

                public void setExpirationTime(long expirationTime) {
                    this.expirationTime = expirationTime;
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

                public String getOwnerId() {
                    return ownerId;
                }

                public void setOwnerId(String ownerId) {
                    this.ownerId = ownerId;
                }
            }
        }

        public static class ServiceTrueEntity {
            private String imGroupId;
            private String ownerId;
            private String hospitalName;
            private long expirationTime;
            private String name;
            private String content;
            private String timeExp;
            private List<String> avatars;

            public String getImGroupId() {
                return imGroupId;
            }

            public void setImGroupId(String imGroupId) {
                this.imGroupId = imGroupId;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public long getExpirationTime() {
                return expirationTime;
            }

            public void setExpirationTime(long expirationTime) {
                this.expirationTime = expirationTime;
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

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }
        }
    }
}
