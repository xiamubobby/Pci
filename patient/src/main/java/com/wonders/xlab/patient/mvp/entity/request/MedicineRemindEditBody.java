package com.wonders.xlab.patient.mvp.entity.request;

import java.util.List;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditBody {

    /**
     * id : 1
     * startDate : 2016-05-04
     * endDate : 2016-05-12
     * remindersTime : 10:00
     * medicationUsages : [{"medicationName":"感冒药","medicationNum":3,"pharmaceuticalUnit":"粒"}]
     * remindersDesc : 记得提醒我啊
     */

    private String id;
    private String startDate;
    private String endDate;
    private String remindersTime;
    private String remindersDesc;
    /**
     * medicationName : 感冒药
     * medicationNum : 3
     * pharmaceuticalUnit : 粒
     */

    private List<MedicationUsagesEntity> medicationUsages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRemindersTime() {
        return remindersTime;
    }

    public void setRemindersTime(String remindersTime) {
        this.remindersTime = remindersTime;
    }

    public String getRemindersDesc() {
        return remindersDesc;
    }

    public void setRemindersDesc(String remindersDesc) {
        this.remindersDesc = remindersDesc;
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
