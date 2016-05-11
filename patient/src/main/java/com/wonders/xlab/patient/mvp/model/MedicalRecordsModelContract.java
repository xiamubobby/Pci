package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.MedicalRecordsEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by jimmy on 16/5/11.
 */
public interface MedicalRecordsModelContract {

    interface Callback extends BaseModelListener {
        void onReceiveMedicalRecordsSuccess(MedicalRecordsEntity entityList);
    }

    interface Actions extends IBaseModel {
        void getMedicalRecordsList(String patientId, int page, int size, Callback callback);
    }
}
