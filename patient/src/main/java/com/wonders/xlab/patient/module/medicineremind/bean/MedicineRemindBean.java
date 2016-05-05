package com.wonders.xlab.patient.module.medicineremind.bean;

import android.databinding.ObservableField;

/**
 * Created by hua on 16/2/24.
 */
public class MedicineRemindBean {
    public ObservableField<String> amOrPmStr = new ObservableField<>();
    public ObservableField<String> timeInStr = new ObservableField<>();
    public ObservableField<String> medicineNameStr = new ObservableField<>();
    public ObservableField<String> expiredDateInStr = new ObservableField<>();
    public ObservableField<Boolean> isChecked = new ObservableField<>();
}
