package com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter.bean;

import android.databinding.ObservableField;

import java.util.List;

/**
 * Created by hua on 16/4/26.
 */
public class PrescriptionBean {
    public ObservableField<Long> recordTimeInMill = new ObservableField<>();
    public ObservableField<String> hospitalName = new ObservableField<>();
    public ObservableField<List<String>> medicineList = new ObservableField<>();
}