package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BasePageEntity;

/**
 * Created by hua on 16/2/25.
 */
public class MedicineRemindListEntity extends BasePageEntity<MedicineRemindListEntity.ContentEntity> {
    public static class ContentEntity {
        private long endDate;
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

        public static class MedicationUsagesEntity {
            private String medicationName;
            private int medicationNum;
            private String pharmaceuticalUnit;

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
        }
    }
}
