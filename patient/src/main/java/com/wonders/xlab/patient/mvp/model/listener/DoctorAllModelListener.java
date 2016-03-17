package com.wonders.xlab.patient.mvp.model.listener;

import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;

import java.util.List;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorAllModelListener extends BaseModelListener {
    void onReceiveAllDoctorListSuccess(List<DoctorAllEntity.RetValuesEntity> valuesEntity);
}
