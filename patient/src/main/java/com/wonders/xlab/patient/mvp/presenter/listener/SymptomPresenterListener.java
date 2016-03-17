package com.wonders.xlab.patient.mvp.presenter.listener;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomPresenterListener extends BasePresenterListener {
    void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

    void onSaveSymptomSuccess(String message);
}
