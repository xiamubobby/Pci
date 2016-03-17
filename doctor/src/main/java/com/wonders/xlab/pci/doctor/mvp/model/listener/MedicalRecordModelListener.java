package com.wonders.xlab.pci.doctor.mvp.model.listener;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import im.hua.library.base.mvp.listener.BasePageModelListener;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordModelListener extends BasePageModelListener {
    void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity);
}
