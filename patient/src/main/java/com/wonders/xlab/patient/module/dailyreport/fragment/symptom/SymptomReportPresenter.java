package com.wonders.xlab.patient.module.dailyreport.fragment.symptom;

import android.text.TextUtils;

import com.wonders.xlab.patient.module.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.SymptomReportLabelBean;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import io.realm.RealmList;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomReportPresenter extends BasePagePresenter implements SymptomReportContract.Presenter {
    private SymptomReportContract.ViewListener mViewListener;
    private SymptomReportContract.Model mSymptomRetrieveModel;

    @Inject
    public SymptomReportPresenter(SymptomReportContract.ViewListener viewListener, SymptomRetrieveModel symptomRetrieveModel) {
        mViewListener = viewListener;
        mSymptomRetrieveModel = symptomRetrieveModel;
        addModel(mSymptomRetrieveModel);
    }

    @Override
    public void getSymptomList(String patientId, long startTime, long endTime, boolean refresh) {
        if (refresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.hideLoading();
            return;
        }
        mSymptomRetrieveModel.getSymptomList(patientId, startTime, endTime, getNextPageIndex(), DEFAULT_PAGE_SIZE, new SymptomReportContract.Callback() {
            @Override
            public void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity) {
                mViewListener.hideLoading();

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
                    mViewListener.appendSymptomList(symptomReportBeanList);
                } else {
                    if (symptomList.size() <= 0) {
                        mViewListener.showEmptyView();
                        return;
                    }
                    mViewListener.showSymptomList(symptomReportBeanList);
                }

            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }
        });
    }

}
