package com.wonders.xlab.pci.mvn.model.task;

import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.task.AddRecordAPI;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.mvn.view.SimpleView;

/**
 * Created by hua on 15/12/18.
 */
public class AddRecordModel extends BaseModel<SimpleEntity> {
    private SimpleView mSimpleView;
    private AddRecordAPI mAddRecordAPI;

    public AddRecordModel(SimpleView simpleView) {
        mSimpleView = simpleView;
        mAddRecordAPI = mRetrofit.create(AddRecordAPI.class);
    }

    public void saveBS(String userId, long date, int timeIndex, double bloodSugarValue) {
        setObservable(mAddRecordAPI.saveBS(userId, date, timeIndex, bloodSugarValue));
    }

    /**
     * @param userId
     * @param date
     * @param heartRate         心率
     * @param systolicPressure  收缩压
     * @param diastolicPressure 舒张压
     */
    public void saveBP(String userId, long date, int heartRate, int systolicPressure, int diastolicPressure) {
        setObservable(mAddRecordAPI.saveBP(userId, date, heartRate, systolicPressure, diastolicPressure));
    }

    public void saveCigarette(String userId, long date, int cigaretteCount) {
        setObservable(mAddRecordAPI.saveCigarette(userId, date, cigaretteCount));
    }

    public void saveWine(String userId, long date, int wineCount, int wineIndex) {
        setObservable(mAddRecordAPI.saveWine(userId, date, wineCount, wineIndex));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSimpleView.svShowLoading();
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mSimpleView.svHideLoading();
        mSimpleView.svSuccess();
    }

    @Override
    protected void onFailed(String message) {
        mSimpleView.svHideLoading();
        mSimpleView.svFailed("保存数据失败，请重试！");
    }
}
