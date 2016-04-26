package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.data.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.model.ISymptomSaveModel;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomSaveModel extends PatientBaseModel<SimpleEntity> implements ISymptomSaveModel {

    private SymptomSaveModelListener mSymptomSaveModelListener;
    private SymptomAPI mSymptomAPI;

    public SymptomSaveModel(SymptomSaveModelListener symptomSaveModelListener) {
        mSymptomSaveModelListener = symptomSaveModelListener;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    /**
     * 保存不适症状
     *
     * @param userId
     * @param symptomIdsStr
     */
    public void saveSymptom(String userId, String[] symptomIdsStr) {
        request(mSymptomAPI.saveSymptom(userId, symptomIdsStr), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mSymptomSaveModelListener.onSaveSymptomSuccess("保存不适症状成功！");
    }

    @Override
    protected void onFailed(int code, String message) {
        mSymptomSaveModelListener.onReceiveFailed(code, "保存数据失败，请重试！");
    }

    public interface SymptomSaveModelListener extends BaseModelListener {
        void onSaveSymptomSuccess(String message);
    }
}
