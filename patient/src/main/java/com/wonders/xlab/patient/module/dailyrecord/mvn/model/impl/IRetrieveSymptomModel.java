package com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/3/16.
 */
public interface IRetrieveSymptomModel extends IBaseModel{
    void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity);
}
