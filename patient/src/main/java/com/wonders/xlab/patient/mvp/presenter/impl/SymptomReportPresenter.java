package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportLabelBean;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;
import com.wonders.xlab.patient.mvp.model.ISymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.presenter.ISymptomReportPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import io.realm.RealmList;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomReportPresenter extends BasePresenter implements ISymptomReportPresenter,SymptomRetrieveModel.SymptomRetrieveModelListener {
    private SymptomReportPresenterListener mListener;
    private ISymptomRetrieveModel mSymptomRetrieveModel;

    public SymptomReportPresenter(SymptomReportPresenterListener listener) {
        mListener = listener;
        mSymptomRetrieveModel = new SymptomRetrieveModel(this);
    }

    @Override
    public void getSymptomList(String patientId) {
        mSymptomRetrieveModel.getSymptomList(patientId);
    }

    @Override
    public void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        List<SymptomRetrieveEntity.RetValuesEntity.ContentEntity> symptomList = valuesEntity.getContent();

        if (null == symptomList || symptomList.size() <= 0) {
            mListener.showEmptyView();
            return;
        }
        List<SymptomReportBean> symptomReportBeanList = new ArrayList<>();
        for (SymptomRetrieveEntity.RetValuesEntity.ContentEntity contentEntity : symptomList) {
            SymptomReportBean bean = new SymptomReportBean();
            bean.setId(contentEntity.getId());
            bean.setAdvice(contentEntity.getDoctorRemark());
            bean.setHasConfirmed(contentEntity.isChecked());
            bean.setRecordTimeInMill(contentEntity.getRecordTime());

            List<SymptomRetrieveEntity.RetValuesEntity.ContentEntity.SymptomsEntity> contentEntitySymptoms = contentEntity.getSymptoms();
            RealmList<SymptomReportLabelBean> symptoms = new RealmList<>();
            for (SymptomRetrieveEntity.RetValuesEntity.ContentEntity.SymptomsEntity symptomsEntity : contentEntitySymptoms) {
                SymptomReportLabelBean labelBean = new SymptomReportLabelBean();
                labelBean.setSymptomStr(symptomsEntity.getName());
                symptoms.add(labelBean);
            }
            bean.setSymptomList(symptoms);

            symptomReportBeanList.add(bean);
        }
        mListener.showTodaysSymtomList(symptomReportBeanList);

    }

    @Override
    public void onReceiveFailed(String message) {
        mListener.hideLoading();
        mListener.showError(message);
    }

    public interface SymptomReportPresenterListener extends BasePresenterListener{
        void showTodaysSymtomList(List<SymptomReportBean> reportBeanList);

        void showEmptyView();
    }
}