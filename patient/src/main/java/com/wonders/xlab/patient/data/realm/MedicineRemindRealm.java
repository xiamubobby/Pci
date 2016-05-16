package com.wonders.xlab.patient.data.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by hua on 16/5/11.
 */
public class MedicineRemindRealm extends RealmObject {
    private String id;
    private long startDate;
    private Long endDate;
    /**
     * 24小时制的时间字符串
     */
    private String remindersTime;

    @Index
    private long remindersTimeInMill;
    /**
     * 过期时间
     * 如果为长期的，则设为0
     */
    private long expireTimeInMill;
    private String remindersDesc;
    private RealmList<MedicationUsagesRealm> medicationUsages;
    private boolean shouldAlarm;

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

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
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

    public RealmList<MedicationUsagesRealm> getMedicationUsages() {
        return medicationUsages;
    }

    public void setMedicationUsages(RealmList<MedicationUsagesRealm> medicationUsages) {
        this.medicationUsages = medicationUsages;
    }

    public long getRemindersTimeInMill() {
        return remindersTimeInMill;
    }

    public void setRemindersTimeInMill(long remindersTimeInMill) {
        this.remindersTimeInMill = remindersTimeInMill;
    }

    public long getExpireTimeInMill() {
        return expireTimeInMill;
    }

    public void setExpireTimeInMill(long expireTimeInMill) {
        this.expireTimeInMill = expireTimeInMill;
    }

    public boolean isShouldAlarm() {
        return shouldAlarm;
    }

    public void setShouldAlarm(boolean shouldAlarm) {
        this.shouldAlarm = shouldAlarm;
    }
}
