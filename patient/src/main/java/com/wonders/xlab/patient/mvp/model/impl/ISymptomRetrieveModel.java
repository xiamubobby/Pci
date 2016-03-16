package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/3/16.
 */
public interface ISymptomRetrieveModel extends IBaseModel{
    void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity);
}
