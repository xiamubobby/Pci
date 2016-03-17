package com.wonders.xlab.patient.mvp.model.listener;

import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorMyModelListener extends BaseModelListener {
    void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity);
}
