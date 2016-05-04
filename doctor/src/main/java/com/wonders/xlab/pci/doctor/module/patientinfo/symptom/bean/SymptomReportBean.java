package com.wonders.xlab.pci.doctor.module.patientinfo.symptom.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.pci.doctor.BR;

import java.util.List;

/**
 * Created by hua on 16/3/21.
 */
public class SymptomReportBean extends BaseObservable {
    private String id;
    private List<SymptomReportLabelBean> symptomList;
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

    public void setSymptomList(List<SymptomReportLabelBean> symptomList) {
        this.symptomList = symptomList;
    }

    @Bindable
    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
        notifyPropertyChanged(BR.advice);
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }

    @Bindable
    public boolean isHasConfirmed() {
        return hasConfirmed;
    }

    public void setHasConfirmed(boolean hasConfirmed) {
        this.hasConfirmed = hasConfirmed;
        notifyPropertyChanged(BR.hasConfirmed);
    }
}
