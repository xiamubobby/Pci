package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/2/25.
 */
public interface IMedicalRecordModel extends IBaseModel {
    void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity);
}
