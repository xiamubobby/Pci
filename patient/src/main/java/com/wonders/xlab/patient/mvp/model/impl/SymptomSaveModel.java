package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomSaveModel extends PatientBaseModel<SimpleEntity> {
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
        fetchData(mAddRecordAPI.saveSymptom(userId, symptomIdsStr), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mSymptomSaveModelListener.onSaveSymptomSuccess("保存不适症状成功！");
    }

    @Override
    protected void onFailed(Throwable e) {
        mSymptomSaveModelListener.onReceiveFailed("保存数据失败，请重试！");
    }

    public interface SymptomSaveModelListener extends BaseModelListener {
        void onSaveSymptomSuccess(String message);
    }
}
