package com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 15/12/18.
 */
public interface IRetrieveSymptomPresenter extends IBasePresenter {
    void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);
}
