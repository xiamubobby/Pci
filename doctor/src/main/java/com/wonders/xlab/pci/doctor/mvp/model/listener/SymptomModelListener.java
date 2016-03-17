package com.wonders.xlab.pci.doctor.mvp.model.listener;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomModelListener extends BaseModelListener {
    void onReceiveSymptomSuccess(SymptomEntity entity);
}
