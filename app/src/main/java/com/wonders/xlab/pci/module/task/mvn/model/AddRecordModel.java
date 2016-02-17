package com.wonders.xlab.pci.module.task.mvn.model;

import com.wonders.xlab.pci.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.api.AddRecordAPI;

/**
 * Created by hua on 15/12/18.
 */
public class AddRecordModel extends BaseModel<SimpleEntity> {
    private MeasureResultView mMeasureResultView;
    private AddRecordAPI mAddRecordAPI;

    public AddRecordModel(MeasureResultView measureResultView) {
        mMeasureResultView = measureResultView;
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
        setObservable(mAddRecordAPI.saveBSSingle(userId, date, timeIndex, bloodSugarValue));
    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        setObservable(mAddRecordAPI.saveBS(userId, bsEntityList));
    }

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {
        setObservable(mAddRecordAPI.saveBP(userId, bpEntityList));
    }

    /**
     * 保存一条血压数据
     *
     * @param userId
     * @param date
     * @param heartRate
     * @param systolicPressure
     * @param diastolicPressure
     */
    public void saveBPSingle(String userId, long date, int heartRate, int systolicPressure, int diastolicPressure) {
        setObservable(mAddRecordAPI.saveBPSingle(userId, date, heartRate, systolicPressure, diastolicPressure));
    }

    /**
     * 保存主诉症状
     *
     * @param userId
     * @param symptomIdsStr
     */
    public void saveSymptom(String userId, String[] symptomIdsStr) {
        setObservable(mAddRecordAPI.saveSymptom(userId, symptomIdsStr));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMeasureResultView.svShowLoading();
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mMeasureResultView.svHideLoading();
        if (response.getRet_code() == 0) {
            mMeasureResultView.svSuccess();
        } else {
            mMeasureResultView.svFailed(response.getMessage());
        }
    }

    @Override
    protected void onFailed(String message) {
        mMeasureResultView.svHideLoading();
        mMeasureResultView.svFailed("保存数据失败，请重试！");
    }
}
