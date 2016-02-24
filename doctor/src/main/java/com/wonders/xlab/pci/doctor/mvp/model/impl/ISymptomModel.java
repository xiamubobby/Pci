package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/2/24.
 */
public interface ISymptomModel extends IBaseModel {
    void onReceiveSymptomSuccess(SymptomEntity entity);
}
