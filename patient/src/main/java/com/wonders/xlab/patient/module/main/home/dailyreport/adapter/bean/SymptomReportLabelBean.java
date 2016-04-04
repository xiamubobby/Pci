package com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean;

import io.realm.RealmObject;

/**
 * Created by hua on 16/3/22.
 */
public class SymptomReportLabelBean extends RealmObject {
    private String symptomStr;

    public String getSymptomStr() {
        return symptomStr;
    }

    public void setSymptomStr(String symptomStr) {
        this.symptomStr = symptomStr;
    }
}
