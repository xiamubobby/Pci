package com.wonders.xlab.patient.module.dailyrecord.mvn.model;


import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.api.AddRecordAPI;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.ISaveBSModel;

import im.hua.library.base.mvp.SimpleEntity;

/**
 * Created by hua on 15/12/18.
 */
public class SaveBSModel extends PatientBaseModel<SimpleEntity> {
    private ISaveBSModel mISaveBSModel;
    private AddRecordAPI mAddRecordAPI;

    public SaveBSModel(ISaveBSModel iSaveBSModel) {
        mISaveBSModel = iSaveBSModel;
        mAddRecordAPI = mRetrofit.create(AddRecordAPI.class);
    }

    /**
     * 保存一条血糖数据
     *
     * @param userId
     * @param date
     * @param timeIndex
     * @param bloodSugarValue
     */
    public void saveBSSingle(String userId, long date, int timeIndex, float bloodSugarValue) {
        fetchData(mAddRecordAPI.saveBSSingle(userId, date, timeIndex, bloodSugarValue), true);
    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        fetchData(mAddRecordAPI.saveBS(userId, bsEntityList), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mISaveBSModel.onSaveBSSuccess("保存血糖成功！");
    }

    @Override
    protected void onFailed(Throwable e) {
        mISaveBSModel.onReceiveFailed("保存血糖失败，请重试！");
    }
}
