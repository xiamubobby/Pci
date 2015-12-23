package com.wonders.xlab.pci.module.record.monitor.mvn.entity;

import com.wonders.xlab.pci.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSEntity extends BaseEntity{

    /**
     * content : [{"id":14,"bloodSuger":12,"bloodSugarTime":0,"recordTime":1450837800000},{"id":13,"bloodSuger":12,"bloodSugarTime":0,"recordTime":1450753140000},{"id":11,"bloodSuger":369,"bloodSugarTime":4,"recordTime":1450694640000},{"id":12,"bloodSuger":122,"bloodSugarTime":0,"recordTime":1450689960000},{"id":3,"bloodSuger":22,"bloodSugarTime":1,"recordTime":1450425326000},{"id":8,"bloodSuger":23,"bloodSugarTime":0,"recordTime":1450368000000},{"id":7,"bloodSuger":12,"bloodSugarTime":0,"recordTime":1450368000000},{"id":6,"bloodSuger":111,"bloodSugarTime":0,"recordTime":1450368000000},{"id":5,"bloodSuger":123,"bloodSugarTime":0,"recordTime":1450368000000},{"id":4,"bloodSuger":123,"bloodSugarTime":0,"recordTime":1450368000000}]
     * last : false
     * totalPages : 2
     * totalElements : 12
     * numberOfElements : 10
     * first : true
     * sort : [{"direction":"DESC","property":"recordTime","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
     * size : 10
     * number : 0
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
        private int numberOfElements;
        private boolean first;
        private int size;
        private int number;
        /**
         * id : 14
         * bloodSuger : 12.0
         * bloodSugarTime : 0
         * recordTime : 1450837800000
         */

        private List<ContentEntity> content;
        /**
         * direction : DESC
         * property : recordTime
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

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setNumber(int number) {
            this.number = number;
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

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public boolean isFirst() {
            return first;
        }

        public int getSize() {
            return size;
        }

        public int getNumber() {
            return number;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public List<SortEntity> getSort() {
            return sort;
        }

        public static class ContentEntity {
            private int id;
            private double bloodSuger;
            private int bloodSugarTime;
            private long recordTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setBloodSuger(double bloodSuger) {
                this.bloodSuger = bloodSuger;
            }

            public void setBloodSugarTime(int bloodSugarTime) {
                this.bloodSugarTime = bloodSugarTime;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public int getId() {
                return id;
            }

            public double getBloodSuger() {
                return bloodSuger;
            }

            public int getBloodSugarTime() {
                return bloodSugarTime;
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
