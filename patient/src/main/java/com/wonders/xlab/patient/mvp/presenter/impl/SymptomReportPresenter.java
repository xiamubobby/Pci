package com.wonders.xlab.patient.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.SymptomReportLabelBean;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;
import com.wonders.xlab.patient.mvp.model.ISymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.presenter.ISymptomReportPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import io.realm.RealmList;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomReportPresenter extends BasePagePresenter implements ISymptomReportPresenter, SymptomRetrieveModel.SymptomRetrieveModelListener {
    private SymptomReportPresenterListener mListener;
    private ISymptomRetrieveModel mSymptomRetrieveModel;

    public SymptomReportPresenter(SymptomReportPresenterListener listener) {
        mListener = listener;
        mSymptomRetrieveModel = new SymptomRetrieveModel(this);
        addModel(mSymptomRetrieveModel);
    }

    @Override
    public void getSymptomList(String patientId, long startTime, long endTime, boolean refresh) {
        if (refresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mListener.hideLoading();
            return;
        }
        mSymptomRetrieveModel.getSymptomList(patientId, startTime, endTime, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<SymptomRetrieveEntity.RetValuesEntity.ContentEntity> symptomList = valuesEntity.getContent();

        List<SymptomReportBean> symptomReportBeanList = new ArrayList<>();
        for (SymptomRetrieveEntity.RetValuesEntity.ContentEntity contentEntity : symptomList) {
            SymptomReportBean bean = new SymptomReportBean();
            bean.setId(contentEntity.getId());
            if (!TextUtils.isEmpty(contentEntity.getDoctorRemark())) {
                bean.setAdvice("医生建议：" + contentEntity.getDoctorRemark());
            }
            bean.setHasConfirmed(contentEntity.isChecked());
            bean.setRecordTimeInMill(contentEntity.getRecordTime());

            List<SymptomRetrieveEntity.RetValuesEntity.ContentEntity.SymptomsEntity> contentEntitySymptoms = contentEntity.getSymptoms();
            List<SymptomReportLabelBean> symptoms = new RealmList<>();
            for (SymptomRetrieveEntity.RetValuesEntity.ContentEntity.SymptomsEntity symptomsEntity : contentEntitySymptoms) {
                SymptomReportLabelBean labelBean = new SymptomReportLabelBean();
                labelBean.setSymptomStr(symptomsEntity.getName());
                symptoms.add(labelBean);
            }
            bean.setSymptomList(symptoms);

            symptomReportBeanList.add(bean);
        }
        if (shouldAppend()) {
            mListener.appendSymptomList(symptomReportBeanList);
        } else {
            if (symptomList.size() <= 0) {
                mListener.showEmptyView();
                return;
            }
            mListener.showSymptomList(symptomReportBeanList);
        }

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.hideLoading();
        mListener.showNetworkError(message);
    }

    public interface SymptomReportPresenterListener extends BasePresenterListener {
        void showSymptomList(List<SymptomReportBean> reportBeanList);

        void appendSymptomList(List<SymptomReportBean> reportBeanList);

        void showEmptyView();
    }
}
