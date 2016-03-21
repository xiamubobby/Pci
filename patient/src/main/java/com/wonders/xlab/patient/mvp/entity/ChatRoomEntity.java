package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/17.
 */
public class ChatRoomEntity extends BaseEntity {

    /**
     * content : [{"id":60,"fromWho":5,"type":"Doctor","name":"( ⊙o⊙ )?","content":"可能","groupId":166710012339552740,"sendTime":1456984359000,"avatarUrl":" "},{"id":28,"fromWho":5,"type":"Doctor","name":"( ⊙o⊙ )?","content":"v广告音乐","groupId":166710012339552740,"sendTime":1456905020000,"avatarUrl":" "},{"id":27,"fromWho":5,"type":"Doctor","name":"( ⊙o⊙ )?","content":"扭扭捏捏","groupId":166710012339552740,"sendTime":1456901509000,"avatarUrl":" "},{"id":18,"fromWho":5,"type":"Doctor","name":"( ⊙o⊙ )?","content":"测试看看","groupId":166710012339552740,"sendTime":1456889834000,"avatarUrl":" "},{"id":14,"fromWho":5,"type":"Doctor","name":"( ⊙o⊙ )?","content":"测试看看","groupId":166710012339552740,"sendTime":1456887039000,"avatarUrl":" "}]
     * last : false
     * totalElements : 18
     * totalPages : 2
     * size : 10
     * number : 0
     * sort : [{"direction":"DESC","property":"lastModifiedDate","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
     * first : true
     * numberOfElements : 10
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
        private String totalElements;
        private String totalPages;
        private String size;
        private String number;
        private boolean first;
        private String numberOfElements;
        /**
         * id : 60
         * fromWho : 5
         * type : Doctor
         * name : ( ⊙o⊙ )?
         * content : 可能
         * groupId : 166710012339552740
         * sendTime : 1456984359000
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

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public String getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(String totalElements) {
            this.totalElements = totalElements;
        }

        public String getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(String totalPages) {
            this.totalPages = totalPages;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public String getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(String numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public List<SortEntity> getSort() {
            return sort;
        }

        public void setSort(List<SortEntity> sort) {
            this.sort = sort;
        }

        public static class ContentEntity {
            private String id;
            private String fromWho;
            private String type;
            private String name;
            private String content;
            private String groupId;
            private long sendTime;
            private String avatarUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFromWho() {
                return fromWho;
            }

            public void setFromWho(String fromWho) {
                this.fromWho = fromWho;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public long getSendTime() {
                return sendTime;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }
        }

        public static class SortEntity {
            private String direction;
            private String property;
            private boolean ignoreCase;
            private String nullHandling;
            private boolean ascending;

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public boolean isIgnoreCase() {
                return ignoreCase;
            }

            public void setIgnoreCase(boolean ignoreCase) {
                this.ignoreCase = ignoreCase;
            }

            public String getNullHandling() {
                return nullHandling;
            }

            public void setNullHandling(String nullHandling) {
                this.nullHandling = nullHandling;
            }

            public boolean isAscending() {
                return ascending;
            }

            public void setAscending(boolean ascending) {
                this.ascending = ascending;
            }
        }
    }
}
