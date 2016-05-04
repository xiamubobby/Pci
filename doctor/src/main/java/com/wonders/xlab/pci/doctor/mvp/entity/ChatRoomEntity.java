package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomEntity extends BaseEntity {

    /**
     * content : [{"id":1,"fromWho":111,"type":"User","name":"张","content":"看看","groupId":1,"sendTime":1456420800000,"avatarUrl":" "},{"id":2,"fromWho":222,"type":"Doctor","name":"三","content":"可能","groupId":1,"sendTime":1456426920000,"avatarUrl":" "}]
     * last : true
     * totalPages : 1
     * totalElements : 2
     * size : 10
     * number : 0
     * sort : [{"direction":"DESC","property":"lastModifiedDate","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
     * first : true
     * numberOfElements : 2
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        /**
         * 是否是最后一页
         */
        private boolean last;
        /**
         * 数据中总共的页数
         */
        private int totalPages;
        /**
         * 数据库中总共的条数
         */
        private int totalElements;
        /**
         * 分页大小
         */
        private int size;
        /**
         * 当前第几页
         */
        private int number;
        /**
         * 是否是第一页
         */
        private boolean first;
        /**
         * 当前返回的这一页的数据条数
         */
        private int numberOfElements;
        /**
         * id : 1
         * fromWho : 111
         * type : User
         * name : 张
         * content : 看看
         * groupId : 1
         * sendTime : 1456420800000
         * avatarUrl :
         */

        private List<ContentEntity> content;
        /**
         * direction : DESC
         * property : lastModifiedDate
         * ignoreCase : false
         * nullHandling : NATIVE
         * ascending : false
         */

        private List<SortEntity> sort;

        public void setLast(boolean last) {
            this.last = last;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public void setSort(List<SortEntity> sort) {
            this.sort = sort;
        }

        public boolean isLast() {
            return last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public int getSize() {
            return size;
        }

        public int getNumber() {
            return number;
        }

        public boolean isFirst() {
            return first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public List<SortEntity> getSort() {
            return sort;
        }

        public static class ContentEntity {
            private int id;
            private int fromWho;
            private String type;
            private String name;
            private String content;
            private String groupId;
            private long sendTime;
            private String avatarUrl;

            public void setId(int id) {
                this.id = id;
            }

            public void setFromWho(int fromWho) {
                this.fromWho = fromWho;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public int getId() {
                return id;
            }

            public int getFromWho() {
                return fromWho;
            }

            public String getType() {
                return type;
            }

            public String getName() {
                return name;
            }

            public String getContent() {
                return content;
            }

            public String getGroupId() {
                return groupId;
            }

            public long getSendTime() {
                return sendTime;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }
        }

        public static class SortEntity {
            private String direction;
            private String property;
            private boolean ignoreCase;
            private String nullHandling;
            private boolean ascending;

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public void setIgnoreCase(boolean ignoreCase) {
                this.ignoreCase = ignoreCase;
            }

            public void setNullHandling(String nullHandling) {
                this.nullHandling = nullHandling;
            }

            public void setAscending(boolean ascending) {
                this.ascending = ascending;
            }

            public String getDirection() {
                return direction;
            }

            public String getProperty() {
                return property;
            }

            public boolean isIgnoreCase() {
                return ignoreCase;
            }

            public String getNullHandling() {
                return nullHandling;
            }

            public boolean isAscending() {
                return ascending;
            }
        }
    }
}
