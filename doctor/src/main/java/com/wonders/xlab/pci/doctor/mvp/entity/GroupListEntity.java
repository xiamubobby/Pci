package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupListEntity extends BaseEntity {

    /**
     * content : [{"servingPeople":0,"avatarUrls":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"servedPeopleCount":0,"multi":true,"ownerHospital":"瑞金医院","ownerDepartment":"儿科","size":1,"ownerName":"刘二","createTime":1459997647000,"serviceUrls":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"name":"XXX心血管小组","id":1,"ownerJobTitle":"副主任医师"},{"servingPeople":0,"avatarUrls":["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"],"servedPeopleCount":0,"multi":false,"ownerHospital":"第一人民医院","ownerDepartment":"儿科","size":3,"ownerName":"小丸子","createTime":1459997647000,"serviceUrls":[],"name":"id为6的诊所","id":10,"ownerJobTitle":"助理医师"}]
     * last : false
     * totalPages : 2
     * totalElements : 2
     * size : 1
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 2
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private boolean last;
        private int totalPages;
        private int totalElements;
        private int size;
        private int number;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        /**
         * servingPeople : 0
         * avatarUrls : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         * servedPeopleCount : 0
         * multi : true
         * ownerHospital : 瑞金医院
         * ownerDepartment : 儿科
         * size : 1
         * ownerName : 刘二
         * createTime : 1459997647000
         * serviceUrls : ["http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg","http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg"]
         * name : XXX心血管小组
         * id : 1
         * ownerJobTitle : 副主任医师
         */

        private List<ContentEntity> content;

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

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
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
            private int servingPeople;
            private int servedPeopleCount;
            private boolean multi;
            private String ownerHospital;
            private String ownerDepartment;
            private int size;
            private String ownerName;
            private String ownerId;
            private long createTime;
            private String name;
            private String id;
            private String ownerJobTitle;
            private List<String> avatarUrls;
            private List<String> serviceUrls;

            public int getServingPeople() {
                return servingPeople;
            }

            public void setServingPeople(int servingPeople) {
                this.servingPeople = servingPeople;
            }

            public int getServedPeopleCount() {
                return servedPeopleCount;
            }

            public void setServedPeopleCount(int servedPeopleCount) {
                this.servedPeopleCount = servedPeopleCount;
            }

            public boolean isMulti() {
                return multi;
            }

            public void setMulti(boolean multi) {
                this.multi = multi;
            }

            public String getOwnerHospital() {
                return ownerHospital;
            }

            public void setOwnerHospital(String ownerHospital) {
                this.ownerHospital = ownerHospital;
            }

            public String getOwnerDepartment() {
                return ownerDepartment;
            }

            public void setOwnerDepartment(String ownerDepartment) {
                this.ownerDepartment = ownerDepartment;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOwnerJobTitle() {
                return ownerJobTitle;
            }

            public void setOwnerJobTitle(String ownerJobTitle) {
                this.ownerJobTitle = ownerJobTitle;
            }

            public List<String> getAvatarUrls() {
                return avatarUrls;
            }

            public void setAvatarUrls(List<String> avatarUrls) {
                this.avatarUrls = avatarUrls;
            }

            public List<String> getServiceUrls() {
                return serviceUrls;
            }

            public void setServiceUrls(List<String> serviceUrls) {
                this.serviceUrls = serviceUrls;
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
