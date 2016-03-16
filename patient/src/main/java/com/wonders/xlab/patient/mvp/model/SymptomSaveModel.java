package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;
import com.wonders.xlab.patient.mvp.model.impl.ISymptomSaveModel;

import im.hua.library.base.mvp.SimpleEntity;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomSaveModel extends PatientBaseModel<SimpleEntity> {
    private ISymptomSaveModel mISymptomSaveModel;
    private AddRecordAPI mAddRecordAPI;

    public SymptomSaveModel(ISymptomSaveModel iSymptomSaveModel) {
        mISymptomSaveModel = iSymptomSaveModel;
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
        mISymptomSaveModel.onSaveSymptomSuccess("保存不适症状成功！");
    }

    @Override
    protected void onFailed(Throwable e) {
        mISymptomSaveModel.onReceiveFailed("保存数据失败，请重试！");
    }
}
