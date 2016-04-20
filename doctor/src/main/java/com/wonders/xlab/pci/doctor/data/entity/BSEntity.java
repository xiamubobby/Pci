package com.wonders.xlab.pci.doctor.data.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BSEntity extends BaseEntity {

    /**
     * content : [{"id":49,"beforeBreakfast":0,"afterBreakfast":6.9,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1460822400000},{"id":43,"beforeBreakfast":6.9,"afterBreakfast":0,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1460390400000},{"id":38,"beforeBreakfast":0,"afterBreakfast":5.9,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1459785600000},{"id":37,"beforeBreakfast":0,"afterBreakfast":0,"beforeLunch":0,"afterLunch":0,"beforeDinner":6.2,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1459353600000},{"id":35,"beforeBreakfast":0,"afterBreakfast":8.5,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1459267200000},{"id":33,"beforeBreakfast":0,"afterBreakfast":0,"beforeLunch":0,"afterLunch":2.9,"beforeDinner":3.9,"afterDinner":6.8,"beforeDawn":0,"recordTime2Long":1459180800000},{"id":32,"beforeBreakfast":0,"afterBreakfast":0,"beforeLunch":0,"afterLunch":5.8,"beforeDinner":0,"afterDinner":0,"beforeDawn":0,"recordTime2Long":1459008000000},{"id":31,"beforeBreakfast":0,"afterBreakfast":0,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":5.3,"beforeDawn":0,"recordTime2Long":1458921600000},{"id":28,"beforeBreakfast":8.2,"afterBreakfast":0,"beforeLunch":0,"afterLunch":0,"beforeDinner":0,"afterDinner":5,"beforeDawn":7.6,"recordTime2Long":1458835200000},{"id":27,"beforeBreakfast":7.6,"afterBreakfast":0,"beforeLunch":7.3,"afterLunch":0,"beforeDinner":0,"afterDinner":2.9,"beforeDawn":0,"recordTime2Long":1458748800000}]
     * totalPages : 3
     * totalElements : 29
     * last : false
     * numberOfElements : 10
     * first : true
     * sort : [{"direction":"DESC","property":"recordTime","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
     * size : 10
     * number : 0
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private int totalPages;
        private int totalElements;
        private boolean last;
        private int numberOfElements;
        private boolean first;
        private int size;
        private int number;
        /**
         * id : 49
         * beforeBreakfast : 0
         * afterBreakfast : 6.9
         * beforeLunch : 0
         * afterLunch : 0
         * beforeDinner : 0
         * afterDinner : 0
         * beforeDawn : 0
         * recordTime2Long : 1460822400000
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

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
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
            private float beforeBreakfast;
            private float afterBreakfast;
            private float beforeLunch;
            private float afterLunch;
            private float beforeDinner;
            private float afterDinner;
            private float beforeDawn;
            private long recordTime2Long;

            public void setId(String id) {
                this.id = id;
            }

            public void setBeforeBreakfast(float beforeBreakfast) {
                this.beforeBreakfast = beforeBreakfast;
            }

            public void setAfterBreakfast(float afterBreakfast) {
                this.afterBreakfast = afterBreakfast;
            }

            public void setBeforeLunch(float beforeLunch) {
                this.beforeLunch = beforeLunch;
            }

            public void setAfterLunch(float afterLunch) {
                this.afterLunch = afterLunch;
            }

            public void setBeforeDinner(float beforeDinner) {
                this.beforeDinner = beforeDinner;
            }

            public void setAfterDinner(float afterDinner) {
                this.afterDinner = afterDinner;
            }

            public void setBeforeDawn(float beforeDawn) {
                this.beforeDawn = beforeDawn;
            }

            public void setRecordTime2Long(long recordTime2Long) {
                this.recordTime2Long = recordTime2Long;
            }

            public String getId() {
                return id;
            }

            public float getBeforeBreakfast() {
                return beforeBreakfast;
            }

            public float getAfterBreakfast() {
                return afterBreakfast;
            }

            public float getBeforeLunch() {
                return beforeLunch;
            }

            public float getAfterLunch() {
                return afterLunch;
            }

            public float getBeforeDinner() {
                return beforeDinner;
            }

            public float getAfterDinner() {
                return afterDinner;
            }

            public float getBeforeDawn() {
                return beforeDawn;
            }

            public long getRecordTime2Long() {
                return recordTime2Long;
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
