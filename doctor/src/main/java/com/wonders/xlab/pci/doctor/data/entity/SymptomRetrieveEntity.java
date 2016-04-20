package com.wonders.xlab.pci.doctor.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomRetrieveEntity extends BaseEntity {

    /**
     * content : [{"id":1,"checked":true,"symptomContent":"都是好的","doctorRemark":"汕头市运动员","recordTimeInStr":1450340368000,"symptoms":[{"id":1,"name":"感冒","symptomUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","new":false},{"id":2,"name":"发热","symptomUrl":"","new":false}]},{"id":2,"checked":true,"symptomContent":"都痛！！","doctorRemark":"你好坏啊！！刘二地方规划Ddddd热热的地方湖光山色归根到底个大概的地方改革丰富的噩噩噩噩噩噩噩噩噩局","recordTimeInStr":1450340368000,"symptoms":[]},{"id":3,"checked":true,"symptomContent":"都痛！！","doctorRemark":"东湖地方Google","recordTimeInStr":1450340368000,"symptoms":[]},{"id":4,"checked":false,"symptomContent":"","doctorRemark":null,"recordTimeInStr":1457073898000,"symptoms":[]},{"id":5,"checked":false,"symptomContent":"","doctorRemark":null,"recordTimeInStr":1457074232000,"symptoms":[]},{"id":6,"checked":false,"symptomContent":"肩膀疼痛,胃疼","doctorRemark":null,"recordTimeInStr":1457318293000,"symptoms":[]},{"id":7,"checked":false,"symptomContent":"后背疼痛,肩膀疼痛","doctorRemark":null,"recordTimeInStr":1457318308000,"symptoms":[]},{"id":8,"checked":false,"symptomContent":"腹痛,牙龈出血,胃疼","doctorRemark":null,"recordTimeInStr":1458287573000,"symptoms":[]}]
     * last : true
     * totalElements : 8
     * totalPages : 1
     * size : 20
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 8
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
        private int totalElements;
        private int totalPages;
        private int size;
        private int number;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        /**
         * id : 1
         * checked : true
         * symptomContent : 都是好的
         * doctorRemark : 汕头市运动员
         * recordTimeInStr : 1450340368000
         * symptoms : [{"id":1,"name":"感冒","symptomUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png","new":false},{"id":2,"name":"发热","symptomUrl":"","new":false}]
         */

        private List<ContentEntity> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
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
            private String id;
            private boolean checked;
            private String symptomContent;
            private String doctorRemark;
            private long recordTime;
            /**
             * id : 1
             * name : 感冒
             * symptomUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/case/Screenshot_2015-12-14-13-22-15.png
             * new : false
             */

            private List<SymptomsEntity> symptoms;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getSymptomContent() {
                return symptomContent;
            }

            public void setSymptomContent(String symptomContent) {
                this.symptomContent = symptomContent;
            }

            public String getDoctorRemark() {
                return doctorRemark;
            }

            public void setDoctorRemark(String doctorRemark) {
                this.doctorRemark = doctorRemark;
            }

            public long getRecordTime() {
                return recordTime;
            }

            public void setRecordTime(long recordTime) {
                this.recordTime = recordTime;
            }

            public List<SymptomsEntity> getSymptoms() {
                return symptoms;
            }

            public void setSymptoms(List<SymptomsEntity> symptoms) {
                this.symptoms = symptoms;
            }

            public static class SymptomsEntity {
                private int id;
                private String name;
                private String symptomUrl;
                @SerializedName("new")
                private boolean newX;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSymptomUrl() {
                    return symptomUrl;
                }

                public void setSymptomUrl(String symptomUrl) {
                    this.symptomUrl = symptomUrl;
                }

                public boolean isNewX() {
                    return newX;
                }

                public void setNewX(boolean newX) {
                    this.newX = newX;
                }
            }
        }
    }
}
