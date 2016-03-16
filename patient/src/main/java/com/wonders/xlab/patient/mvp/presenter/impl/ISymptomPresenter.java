package com.wonders.xlab.patient.mvp.presenter.impl;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 15/12/18.
 */
public interface ISymptomPresenter extends IBasePresenter {
    void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

    void onSaveSymptomSuccess(String message);
}
