package com.wonders.xlab.patient.mvp.entity.request;

import com.wonders.xlab.patient.mvp.entity.MedicationUsagesEntity;

import java.util.List;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditBody {

    /**
     * 如果是修改，则需要设置id
     */
    private String id;
    private long startDate;
    private Long endDate;
    private String remindersTime;
    private String remindersDesc;
    private List<MedicationUsagesEntity> medicationUsages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
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

}
