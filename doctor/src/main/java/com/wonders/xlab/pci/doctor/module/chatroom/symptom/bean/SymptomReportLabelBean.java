package com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.pci.doctor.BR;

/**
 * Created by hua on 16/3/22.
 */
public class SymptomReportLabelBean extends BaseObservable {
    private String symptomStr;

    @Bindable
    public String getSymptomStr() {
        return symptomStr;
    }

    public void setSymptomStr(String symptomStr) {
        this.symptomStr = symptomStr;
        notifyPropertyChanged(BR.symptomStr);
    }
}