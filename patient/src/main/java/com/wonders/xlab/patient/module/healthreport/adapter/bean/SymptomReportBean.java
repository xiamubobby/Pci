package com.wonders.xlab.patient.module.healthreport.adapter.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hua on 16/3/21.
 */
public class SymptomReportBean extends RealmObject{
    private String id;
    private RealmList<SymptomReportLabelBean> symptomList;
    private String advice;
    private long recordTimeInMill;
    private boolean hasConfirmed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SymptomReportLabelBean> getSymptomList() {
        return symptomList;
    }

    public void setSymptomList(RealmList<SymptomReportLabelBean> symptomList) {
        this.symptomList = symptomList;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }

    public boolean isHasConfirmed() {
        return hasConfirmed;
    }

    public void setHasConfirmed(boolean hasConfirmed) {
        this.hasConfirmed = hasConfirmed;
    }
}
