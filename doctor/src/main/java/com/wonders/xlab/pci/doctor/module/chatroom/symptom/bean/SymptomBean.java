package com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.pci.doctor.BR;

/**
 * Created by hua on 16/2/23.
 */
public class SymptomBean extends BaseObservable{
    private String symptomId;
    private String timeStr;
    private boolean isChecked;
    private String symptomStr;
    private String comment;

    @Bindable
    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    @Bindable
    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
        notifyPropertyChanged(BR.isChecked);
    }

    @Bindable
    public String getSymptomStr() {
        return symptomStr;
    }

    public void setSymptomStr(String symptomStr) {
        this.symptomStr = symptomStr;
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

    @Bindable
    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }
}
