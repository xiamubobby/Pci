package com.wonders.xlab.pci.module.record.monitor.mvn.entity;

import com.wonders.xlab.pci.mvn.BaseEntity;

import java.util.List;

/**
 * Created by sjy on 2015/12/22.
 */
public class BPEntity extends BaseEntity {

    /**
     * content : [{"id":4,"heartRate":55,"systolicPressure":121,"diastolicPressure":55,"recordTime":1450690020000},{"id":3,"heartRate":123,"systolicPressure":23,"diastolicPressure":233,"recordTime":1450686600000}]
     * last : true
     * totalPages : 1
     * totalElements : 2
     * numberOfElements : 2
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
         * id : 4
         * heartRate : 55
         * systolicPressure : 121
         * diastolicPressure : 55
         * recordTime : 1450690020000
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
            private int heartRate;
            private int systolicPressure;
            private int diastolicPressure;
            private long recordTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setHeartRate(int heartRate) {
                this.heartRate = heartRate;
            }

            public void setSystolicPressure(int systolicPressure) {
                this.systolicPressure = systolicPressure;
            }

            public void setDiastolicPressure(int diastolicPressure) {
                this.diastolicPressure = diastolicPressure;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public int getId() {
                return id;
            }

            public int getHeartRate() {
                return heartRate;
            }

            public int getSystolicPressure() {
                return systolicPressure;
            }

            public int getDiastolicPressure() {
                return diastolicPressure;
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
