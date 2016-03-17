package com.wonders.xlab.patient.mvp.model.listener;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/16.
 */
public interface SymptomRetrieveModelListener extends BaseModelListener {
    void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity);
}
