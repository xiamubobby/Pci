package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import im.hua.library.base.mvp.impl.IBasePageModel;

/**
 * Created by hua on 16/2/25.
 */
public interface IMedicalRecordModel extends IBasePageModel {
    void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity);
}
