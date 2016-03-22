package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportLabelBean;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;

import java.util.Calendar;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;
import io.realm.RealmList;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomSaveModel extends PatientBaseModel<SimpleEntity> {
    private SymptomReportBean mSymptomReportBeanTmp = new SymptomReportBean();

    private SymptomSaveModelListener mSymptomSaveModelListener;
    private AddRecordAPI mAddRecordAPI;

    public SymptomSaveModel(SymptomSaveModelListener symptomSaveModelListener) {
        mSymptomSaveModelListener = symptomSaveModelListener;
        mAddRecordAPI = mRetrofit.create(AddRecordAPI.class);
    }

    /**
     * 保存不适症状
     *
     * @param userId
     * @param symptomIdsStr
     */
    public void saveSymptom(String userId, String[] symptomIdsStr) {
        mSymptomReportBeanTmp.setRecordTimeInMill(Calendar.getInstance().getTimeInMillis());
        mSymptomReportBeanTmp.setHasConfirmed(false);
        mSymptomReportBeanTmp.setAdvice("");

        RealmList<SymptomReportLabelBean> labelBeanList = new RealmList<>();
        for (String symptom : symptomIdsStr) {
            SymptomReportLabelBean bean = new SymptomReportLabelBean();
            bean.setSymptomStr(symptom);
            labelBeanList.add(bean);
        }
        mSymptomReportBeanTmp.setSymptomList(labelBeanList);

        fetchData(mAddRecordAPI.saveSymptom(userId, symptomIdsStr), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        /**
         * save
         */
        XApplication.realm.beginTransaction();
        SymptomReportBean bean = XApplication.realm.copyToRealm(mSymptomReportBeanTmp);
        XApplication.realm.commitTransaction();

        mSymptomSaveModelListener.onSaveSymptomSuccess("保存不适症状成功！");
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mSymptomSaveModelListener.onReceiveFailed("保存数据失败，请重试！");
    }

    public interface SymptomSaveModelListener extends BaseModelListener {
        void onSaveSymptomSuccess(String message);
    }
}
