package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BloodSugarEntity extends BaseEntity {

    /**
     * content : [{"id":1,"beforeBreakfast":4,"afterBreakfast":1,"beforeLunch":6,"afterLunch":3,"beforeDinner":6,"afterDinner":2,"beforeDawn":5,"randomValue":7,"recordTime2Long":1451577600000}]
     * last : true
     * totalElements : 1
     * totalPages : 1
     * size : 10
     * number : 0
     * sort : [{"direction":"DESC","property":"recordTimeInStr","ignoreCase":false,"nullHandling":"NATIVE","ascending":false}]
     * first : true
     * numberOfElements : 1
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
        private boolean first;
        private int numberOfElements;
        /**
         * id : 1
         * beforeBreakfast : 4
         * afterBreakfast : 1
         * beforeLunch : 6
         * afterLunch : 3
         * beforeDinner : 6
         * afterDinner : 2
         * beforeDawn : 5
         * randomValue : 7
         * recordTime2Long : 1451577600000
         */

        private List<ContentEntity> content;
        /**
         * direction : DESC
         * property : recordTimeInStr
         * ignoreCase : false
         * nullHandling : NATIVE
         * ascending : false
         */

        private List<SortEntity> sort;

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
            private float beforeBreakfast;
            private float afterBreakfast;
            private float beforeLunch;
            private float afterLunch;
            private float beforeDinner;
            private float afterDinner;
            private float beforeDawn;
            private long recordTime2Long;
            private int beforeBreakfastStandard;
            private int afterBreakfastStandard;
            private int beforeLunchStandard;
            private int afterLunchStandard;
            private int beforeDinnerStandard;
            private int afterDinnerStandard;
            private int beforeDawnStandard;

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

            public int getBeforeBreakfastStandard() {
                return beforeBreakfastStandard;
            }

            public void setBeforeBreakfastStandard(int beforeBreakfastStandard) {
                this.beforeBreakfastStandard = beforeBreakfastStandard;
            }

            public int getAfterBreakfastStandard() {
                return afterBreakfastStandard;
            }

            public void setAfterBreakfastStandard(int afterBreakfastStandard) {
                this.afterBreakfastStandard = afterBreakfastStandard;
            }

            public int getBeforeLunchStandard() {
                return beforeLunchStandard;
            }

            public void setBeforeLunchStandard(int beforeLunchStandard) {
                this.beforeLunchStandard = beforeLunchStandard;
            }

            public int getAfterLunchStandard() {
                return afterLunchStandard;
            }

            public void setAfterLunchStandard(int afterLunchStandard) {
                this.afterLunchStandard = afterLunchStandard;
            }

            public int getBeforeDinnerStandard() {
                return beforeDinnerStandard;
            }

            public void setBeforeDinnerStandard(int beforeDinnerStandard) {
                this.beforeDinnerStandard = beforeDinnerStandard;
            }

            public int getAfterDinnerStandard() {
                return afterDinnerStandard;
            }

            public void setAfterDinnerStandard(int afterDinnerStandard) {
                this.afterDinnerStandard = afterDinnerStandard;
            }

            public int getBeforeDawnStandard() {
                return beforeDawnStandard;
            }

            public void setBeforeDawnStandard(int beforeDawnStandard) {
                this.beforeDawnStandard = beforeDawnStandard;
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
