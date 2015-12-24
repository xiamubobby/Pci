package com.wonders.xlab.pci.mvn.entity.home;

import com.google.gson.annotations.SerializedName;
import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class HomeEntity extends BaseEntity {

    /**
     * content : [{"id":3,"user":"","name":"华医师","portrait":"","title":"今日任务小结","content":"但是劳斯莱斯劳斯莱斯了","createdDate":1450080990833,"lastModifiedDate":1450080990833,"new":false},{"id":2,"user":"","name":"敏医师","portrait":"","title":"昨日任务小结","content":"短发啦啦啦啦啦","createdDate":1450080990832,"lastModifiedDate":1450080990832,"new":false},{"id":1,"user":"","name":"张医师","portrait":"","title":"昨日健康任务小结","content":"dfdlfjlaj","createdDate":1450080990831,"lastModifiedDate":1450080990831,"new":false}]
     * last : true
     * totalElements : 3
     * totalPages : 1
     * size : 20
     * number : 0
     * sort :
     * first : true
     * numberOfElements : 3
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private boolean last;
        private int totalElements;
        private int totalPages;
        private int size;
        private int number;
        private String sort;
        private boolean first;
        private int numberOfElements;
        /**
         * id : 3
         * user :
         * name : 华医师
         * portrait :
         * title : 今日任务小结
         * content : 但是劳斯莱斯劳斯莱斯了
         * createdDate : 1450080990833
         * lastModifiedDate : 1450080990833
         * new : false
         */

        private List<ContentEntity> content;

        public void setLast(boolean last) {
            this.last = last;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public boolean isLast() {
            return last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getSize() {
            return size;
        }

        public int getNumber() {
            return number;
        }

        public String getSort() {
            return sort;
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

        public static class ContentEntity {
            private int id;
            private String user;
            private String name;
            private String portrait;
            private String title;
            private String content;
            private long createdDate;
            private long lastModifiedDate;
            @SerializedName("new")
            private boolean newX;

            public void setId(int id) {
                this.id = id;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public void setLastModifiedDate(long lastModifiedDate) {
                this.lastModifiedDate = lastModifiedDate;
            }

            public void setNewX(boolean newX) {
                this.newX = newX;
            }

            public int getId() {
                return id;
            }

            public String getUser() {
                return user;
            }

            public String getName() {
                return name;
            }

            public String getPortrait() {
                return portrait;
            }

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public long getLastModifiedDate() {
                return lastModifiedDate;
            }

            public boolean isNewX() {
                return newX;
            }
        }
    }
}
