package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/2/22.
 */
public interface IPatientModel extends IBaseModel {
    void onReceivePatientSuccess(PatientEntity entity);
}
