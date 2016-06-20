package com.wonders.xlab.patient.module.dailyreport.fragment.symptom;

import com.wonders.xlab.patient.module.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;

import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/20.
 */
public interface SymptomReportContract {
    interface Callback extends BaseModelListener {
        void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity);
    }

    interface Model extends IBaseModel {
        void getSymptomList(String patientId, long startTime, long endTime, int page, int size, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showSymptomList(List<SymptomReportBean> reportBeanList);

        void appendSymptomList(List<SymptomReportBean> reportBeanList);

        void showEmptyView();
    }

    interface Presenter extends IBasePresenter {
        void getSymptomList(String patientId, long startTime, long endTime, boolean refresh);
    }
}
