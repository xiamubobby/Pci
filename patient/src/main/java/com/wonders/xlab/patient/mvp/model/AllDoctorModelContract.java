package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/5.
 */
public interface AllDoctorModelContract {
    interface Callback extends BaseModelListener{
        void onReceiveAllDoctorListSuccess(DoctorAllEntity valuesEntity);
    }

    interface Actions extends IBaseModel{
        void getAllDoctorList(String patientId, int page, int pageSize, Callback callback);
    }
}
