package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class MedicineRemindEntity extends BaseEntity {


    /**
     * endDate : 2016-05-23
     * expire : false
     * remindersTime : 10:00
     * id : 1
     * remindersDesc : 记得提醒我啊
     * startDate : 2016-04-23
     * medicationUsages : [{"id":1,"startDate":"2016-04-23","endDate":"2016-05-23","remindersTime":"10:00","remindersDesc":"记得提醒我啊","delete":false,"expire":false},"medicationName":"感冒药","medicationNum":3,"pharmaceuticalUnit":"粒","use":true}]
     */

    private List<RetValuesEntity> ret_values;

    public List<RetValuesEntity> getRet_values() {
        return ret_values;
    }

    public void setRet_values(List<RetValuesEntity> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String endDate;
        private boolean expire;
        private String remindersTime;
        private int id;
        private String remindersDesc;
        private String startDate;
        /**
         * id : 1
         * medicationName : 感冒药
         * medicationNum : 3
         * pharmaceuticalUnit : 粒
         * use : true
         */

        private List<MedicationUsagesEntity> medicationUsages;

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public boolean isExpire() {
            return expire;
        }

        public void setExpire(boolean expire) {
            this.expire = expire;
        }

        public String getRemindersTime() {
            return remindersTime;
        }

        public void setRemindersTime(String remindersTime) {
            this.remindersTime = remindersTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRemindersDesc() {
            return remindersDesc;
        }

        public void setRemindersDesc(String remindersDesc) {
            this.remindersDesc = remindersDesc;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public List<MedicationUsagesEntity> getMedicationUsages() {
            return medicationUsages;
        }

        public void setMedicationUsages(List<MedicationUsagesEntity> medicationUsages) {
            this.medicationUsages = medicationUsages;
        }

        public static class MedicationUsagesEntity {
            private int id;
            private String medicationName;
            private int medicationNum;
            private String pharmaceuticalUnit;
            private boolean use;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMedicationName() {
                return medicationName;
            }

            public void setMedicationName(String medicationName) {
                this.medicationName = medicationName;
            }

            public int getMedicationNum() {
                return medicationNum;
            }

            public void setMedicationNum(int medicationNum) {
                this.medicationNum = medicationNum;
            }

            public String getPharmaceuticalUnit() {
                return pharmaceuticalUnit;
            }

            public void setPharmaceuticalUnit(String pharmaceuticalUnit) {
                this.pharmaceuticalUnit = pharmaceuticalUnit;
            }

            public boolean isUse() {
                return use;
            }

            public void setUse(boolean use) {
                this.use = use;
            }

        }
    }
}
