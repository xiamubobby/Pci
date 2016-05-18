package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public interface DoctorMyModelContract {
    interface Callback extends BaseModelListener{
        void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity);
    }

    interface Actions extends IBaseModel {
        void getMyDoctors(String patientId, int page, int pageSize, Callback callback);
    }
}