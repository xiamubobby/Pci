package com.wonders.xlab.pci.doctor.module.patient.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.wonders.xlab.pci.doctor.BR;


/**
 * Created by hua on 16/2/19.
 */
public class PatientBean extends BaseObservable {
    private String patientId;
    private String portrait;
    private String patientName;
    private String gender;
    private String age;
    private String timeAfterSurgery;
    private String history;
    private String phoneNumber;

    @Bindable
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
        notifyPropertyChanged(BR.portrait);
    }

    @Bindable
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
        notifyPropertyChanged(BR.patientName);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getTimeAfterSurgery() {
        return timeAfterSurgery;
    }

    public void setTimeAfterSurgery(String timeAfterSurgery) {
        this.timeAfterSurgery = timeAfterSurgery;
        notifyPropertyChanged(BR.timeAfterSurgery);
    }

    @Bindable
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
        notifyPropertyChanged(BR.history);
    }

    @Bindable
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
        notifyPropertyChanged(BR.patientId);
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }
}
