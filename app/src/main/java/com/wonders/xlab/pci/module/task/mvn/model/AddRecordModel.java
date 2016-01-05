package com.wonders.xlab.pci.module.task.mvn.model;

import com.wonders.xlab.pci.assist.connection.entity.BPEntityList;
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

    public void saveBS(String userId, long date, int timeIndex, float bloodSugarValue) {
        setObservable(mAddRecordAPI.saveBS(userId, date, timeIndex, bloodSugarValue));
    }

    /**
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {

        setObservable(mAddRecordAPI.saveBP(userId, bpEntityList));
    }

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

        }
    }

    @Override
    protected void onFailed(String message) {
        mMeasureResultView.svHideLoading();
        mMeasureResultView.svFailed("保存数据失败，请重试！");
    }
}
