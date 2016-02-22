package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

/**
 * Created by hua on 16/2/22.
 */
public interface IPatientModel {
    void onReceivePatientSuccess(PatientEntity entity);
    void onReceivePatientFailed(PatientEntity entity);
}
