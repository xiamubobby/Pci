package com.wonders.xlab.patient.module.medicineremind.bean;

import java.util.List;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindDataBean extends MedicineRemindBean {

    private int id;
    private String timePeriod;
    private String timeStr;
    private boolean isRemind;
    private List<Medicine> medicines;
    private String startTime;
    private String endTime;
    private String reminderDesc;

    @Override
    public int getItemLayout() {
        return ITEM_LAYOUT_MEDICINE_REMIND;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReminderDesc() {
        return reminderDesc;
    }

    public void setReminderDesc(String reminderDesc) {
        this.reminderDesc = reminderDesc;
    }

    public static class Medicine {
        private int id;
        private String medicineName;
        private int medicineNum;
        private String medicineUnit;
        private boolean use;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public int getMedicineNum() {
            return medicineNum;
        }

        public void setMedicineNum(int medicineNum) {
            this.medicineNum = medicineNum;
        }

        public String getMedicineUnit() {
            return medicineUnit;
        }

        public void setMedicineUnit(String medicineUnit) {
            this.medicineUnit = medicineUnit;
        }

        public boolean isUse() {
            return use;
        }

        public void setUse(boolean use) {
            this.use = use;
        }
    }
}
