package com.wonders.xlab.patient.module.dailyreport.datarecord.symptom;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/30.
 */
public interface SymptomContract {

    interface Callback extends BaseModelListener {
        void onSaveSymptomSuccess(String message, Callback callback);

        void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity, Callback callback);
    }

    interface Model extends IBaseModel {
        void getSymptoms();

        void saveSymptom(String userId, String[] symptomIdsStr);
    }

    interface ViewListener extends BasePresenterListener {
        void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

        void onSaveSymptomSuccess(String message);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取全部不适症状
         */
        void getSymptoms();

        /**
         * 保存不适症状
         *
         * @param patientId
         * @param symptomIdsStr
         */
        void saveSymptom(String patientId, String[] symptomIdsStr);
    }
}
