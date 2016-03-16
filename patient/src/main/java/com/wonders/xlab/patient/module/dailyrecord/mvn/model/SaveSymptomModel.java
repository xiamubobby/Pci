package com.wonders.xlab.patient.module.dailyrecord.mvn.model;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.api.AddRecordAPI;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.ISaveSymptomModel;

import im.hua.library.base.mvp.SimpleEntity;

/**
 * Created by hua on 15/12/18.
 */
public class SaveSymptomModel extends PatientBaseModel<SimpleEntity> {
    private ISaveSymptomModel mISaveSymptomModel;
    private AddRecordAPI mAddRecordAPI;

    public SaveSymptomModel(ISaveSymptomModel iSaveSymptomModel) {
        mISaveSymptomModel = iSaveSymptomModel;
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
        mISaveSymptomModel.onSaveSymptomSuccess("保存不适症状成功！");
    }

    @Override
    protected void onFailed(Throwable e) {
        mISaveSymptomModel.onReceiveFailed("保存数据失败，请重试！");
    }
}
