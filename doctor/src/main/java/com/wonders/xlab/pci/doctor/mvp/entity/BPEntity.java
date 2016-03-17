package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BPEntity extends BaseEntity {

    /**
     * line : {"systolic":[174,166,177,166,178,163],"diastolic":[88,89,76,98,78,91],"recordTime":[1454256000000,1454342400000,1454428800000,1454515200000,1454601600000,1454688000000],"heartRates":[85,76,89,80,74,88]}
     * table : {"content":[{"id":41,"heartRate":85,"systolicPressure":174,"diastolicPressure":88,"recordTime":1454256000000},{"id":40,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":39,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":38,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":37,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":36,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":35,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":34,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000},{"id":33,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000},{"id":32,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000}],"last":false,"totalPages":2,"totalElements":17,"size":10,"number":0,"first":true,"sort":[{"direction":"DESC","property":"recordTime","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}],"numberOfElements":10}
     */

    private RetValuesEntity ret_values;

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public static class RetValuesEntity {
        private LineEntity line;
        /**
         * content : [{"id":41,"heartRate":85,"systolicPressure":174,"diastolicPressure":88,"recordTime":1454256000000},{"id":40,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":39,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":38,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045963000},{"id":37,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":36,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":35,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045913000},{"id":34,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000},{"id":33,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000},{"id":32,"heartRate":67,"systolicPressure":123,"diastolicPressure":68,"recordTime":1452045881000}]
         * last : false
         * totalPages : 2
         * totalElements : 17
         * size : 10
         * number : 0
         * first : true
         * sort : [{"direction":"DESC","property":"recordTime","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
         * numberOfElements : 10
         */

        private TableEntity table;

        public void setLine(LineEntity line) {
            this.line = line;
        }

        public void setTable(TableEntity table) {
            this.table = table;
        }

        public LineEntity getLine() {
            return line;
        }

        public TableEntity getTable() {
            return table;
        }

        public static class LineEntity {
            private List<Integer> systolic;
            private List<Integer> diastolic;
            private List<Long> recordTime;
            private List<Integer> heartRates;

            public void setSystolic(List<Integer> systolic) {
                this.systolic = systolic;
            }

            public void setDiastolic(List<Integer> diastolic) {
                this.diastolic = diastolic;
            }

            public void setRecordTime(List<Long> recordTime) {
                this.recordTime = recordTime;
            }

            public void setHeartRates(List<Integer> heartRates) {
                this.heartRates = heartRates;
            }

            public List<Integer> getSystolic() {
                return systolic;
            }

            public List<Integer> getDiastolic() {
                return diastolic;
            }

            public List<Long> getRecordTime() {
                return recordTime;
            }

            public List<Integer> getHeartRates() {
                return heartRates;
            }
        }

        public static class TableEntity {
            private boolean last;
            private int totalPages;
            private int totalElements;
            private int size;
            private int number;
            private boolean first;
            private int numberOfElements;
            /**
             * id : 41
             * heartRate : 85
             * systolicPressure : 174
             * diastolicPressure : 88
             * recordTime : 1454256000000
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
}
