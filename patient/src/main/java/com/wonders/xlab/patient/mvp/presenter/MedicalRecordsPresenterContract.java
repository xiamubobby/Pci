package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.bean.MedicalRecordsBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/11.
 */
public interface MedicalRecordsPresenterContract {

    interface ViewListener extends BasePagePresenterListener {
        void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList);

        void appendMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList);
    }

    interface Actions extends IBasePresenter {
        void getMedicalRecordsList(String patientId, boolean isRefresh);
    }
}
