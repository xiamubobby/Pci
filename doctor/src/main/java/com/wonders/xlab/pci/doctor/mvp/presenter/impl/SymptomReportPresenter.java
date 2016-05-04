package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import android.text.TextUtils;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.ISymptomRetrieveModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SymptomCommentModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SymptomRetrieveModel;
import com.wonders.xlab.pci.doctor.module.patientinfo.symptom.bean.SymptomReportBean;
import com.wonders.xlab.pci.doctor.module.patientinfo.symptom.bean.SymptomReportLabelBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.ISymptomReportPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomReportPresenter extends BasePagePresenter implements ISymptomReportPresenter, SymptomRetrieveModel.SymptomRetrieveModelListener, SymptomCommentModel.SymptomCommentModelListener {
    private SymptomReportPresenterListener mListener;
    private ISymptomRetrieveModel mSymptomRetrieveModel;
    private SymptomCommentModel mSymptomCommentModel;

    public SymptomReportPresenter(SymptomReportPresenterListener listener) {
        mListener = listener;
        mSymptomRetrieveModel = new SymptomRetrieveModel(this);
        mSymptomCommentModel = new SymptomCommentModel(this);
        addModel(mSymptomRetrieveModel);
        addModel(mSymptomCommentModel);
    }

    @Override
    public void saveComment(String symptomId, String doctorId, String comment, boolean check) {
        mSymptomCommentModel.saveComment(symptomId, doctorId, comment, check);
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
        mSymptomRetrieveModel.getSymptomList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveSymptomListSuccess(SymptomEntity.RetValuesEntity valuesEntity) {
        mListener.hideLoading();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<SymptomEntity.RetValuesEntity.ContentEntity> symptomList = valuesEntity.getContent();

        List<SymptomReportBean> symptomReportBeanList = new ArrayList<>();
        for (SymptomEntity.RetValuesEntity.ContentEntity contentEntity : symptomList) {
            SymptomReportBean bean = new SymptomReportBean();
            bean.setId(contentEntity.getId());
            if (!TextUtils.isEmpty(contentEntity.getDoctorRemark())) {
                bean.setAdvice(contentEntity.getDoctorRemark());
            }
            bean.setHasConfirmed(contentEntity.isChecked());
            bean.setRecordTimeInMill(contentEntity.getRecordTime());

            List<SymptomEntity.RetValuesEntity.ContentEntity.SymptomsEntity> contentEntitySymptoms = contentEntity.getSymptoms();
            List<SymptomReportLabelBean> symptoms = new ArrayList<>();
            for (SymptomEntity.RetValuesEntity.ContentEntity.SymptomsEntity symptomsEntity : contentEntitySymptoms) {
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
                mListener.showEmptyView("");
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

    @Override
    public void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity) {
        mListener.saveCommentSuccess();
    }

    public interface SymptomReportPresenterListener extends BasePresenterListener {
        void showSymptomList(List<SymptomReportBean> reportBeanList);

        void appendSymptomList(List<SymptomReportBean> reportBeanList);

        void saveCommentSuccess();
    }
}
