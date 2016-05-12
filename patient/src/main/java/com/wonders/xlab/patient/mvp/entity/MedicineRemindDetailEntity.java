package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/5/9.
 */
public class MedicineRemindDetailEntity extends BaseEntity {

    /**
     * endDate : 2016-05-23
     * expire : false
     * remindersTime : 10:00
     * id : 1
     * remindersDesc : 记得提醒我啊
     * startDate : 2016-04-23
     * medicationUsages : [{"medicationName":"感冒药","medicationNum":3,"pharmaceuticalUnit":"粒"}]
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private Long endDate;
        private boolean expire;
        private String remindersTime;
        private String id;
        private String remindersDesc;
        private long startDate;
        /**
         * medicationName : 感冒药
         * medicationNum : 3
         * pharmaceuticalUnit : 粒
         */

        private List<MedicationUsagesEntity> medicationUsages;

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemindersDesc() {
            return remindersDesc;
        }

        public void setRemindersDesc(String remindersDesc) {
            this.remindersDesc = remindersDesc;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public List<MedicationUsagesEntity> getMedicationUsages() {
            return medicationUsages;
        }

        public void setMedicationUsages(List<MedicationUsagesEntity> medicationUsages) {
            this.medicationUsages = medicationUsages;
        }
    }
}
