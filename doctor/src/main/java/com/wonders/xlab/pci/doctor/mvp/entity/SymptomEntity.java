package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/24.
 */
public class SymptomEntity extends BaseEntity {

    /**
     * content : [{"id":1,"checked":false,"symptomContent":"都痛","doctorRemark":"你好坏啊","recordTime":1450340368000},{"id":2,"checked":false,"symptomContent":"都痛！！","doctorRemark":"你好坏啊！！","recordTime":1450340368000}]
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
        private boolean last;
        private int totalPages;
        private int totalElements;
        private int size;
        private int number;
        private boolean first;
        private int numberOfElements;
        /**
         * id : 1
         * checked : false
         * symptomContent : 都痛
         * doctorRemark : 你好坏啊
         * recordTime : 1450340368000
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
            private String id;
            private boolean checked;
            private String symptomContent;
            private String doctorRemark;
            private long recordTime;

            public void setId(String id) {
                this.id = id;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public void setSymptomContent(String symptomContent) {
                this.symptomContent = symptomContent;
            }

            public void setDoctorRemark(String doctorRemark) {
                this.doctorRemark = doctorRemark;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public String getId() {
                return id;
            }

            public boolean isChecked() {
                return checked;
            }

            public String getSymptomContent() {
                return symptomContent;
            }

            public String getDoctorRemark() {
                return doctorRemark;
            }

            public long getRecordTime() {
                return recordTime;
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
