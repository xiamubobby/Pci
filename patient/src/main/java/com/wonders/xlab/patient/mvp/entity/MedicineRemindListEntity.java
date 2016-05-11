package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BasePageEntity;

/**
 * Created by hua on 16/2/25.
 */
public class MedicineRemindListEntity extends BasePageEntity<MedicineRemindListEntity.ContentEntity> {
    public static class ContentEntity {
        private String id;
        private long startDate;
        private Long endDate;
        private String remindersTime;
        private String remindersDesc;
        private boolean expire;
        private boolean manualCloseReminder;
        /**
         * medicationName : 感冒药
         * medicationNum : 3
         * pharmaceuticalUnit : 粒
         */

        private List<MedicationUsagesEntity> medicationUsages;

        /**
         * 为null，则表示长期
         * @return
         */
        public Long getEndDate() {
            return endDate;
        }

        public void setEndDate(Long endDate) {
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

        public boolean isManualCloseReminder() {
            return manualCloseReminder;
        }

        public void setManualCloseReminder(boolean manualCloseReminder) {
            this.manualCloseReminder = manualCloseReminder;
        }

    }
}
