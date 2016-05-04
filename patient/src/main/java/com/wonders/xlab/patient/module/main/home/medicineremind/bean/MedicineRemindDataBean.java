package com.wonders.xlab.patient.module.main.home.medicineremind.bean;

import java.util.ArrayList;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindDataBean extends MedicineRemindBean {

    private String timeStr;
    private boolean isRemind;
    private ArrayList<String> medicines;
    private String endTime;

    @Override
    public int getItemLayout() {
        return ITEM_LAYOUT_MEDICINE_REMIND;
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

    public ArrayList<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<String> medicines) {
        this.medicines = medicines;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
