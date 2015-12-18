package com.wonders.xlab.pci.mvn.model.task;

import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.task.SymptomAPI;
import com.wonders.xlab.pci.mvn.entity.task.SymptomEntity;
import com.wonders.xlab.pci.mvn.view.task.SymptomView;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomModel extends BaseModel<SymptomEntity> {

    private SymptomView mSymptomView;
    private SymptomAPI mSymptomAPI;

    public SymptomModel(SymptomView symptomView) {
        mSymptomView = symptomView;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms() {
        setObservable(mSymptomAPI.getSymptoms());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSymptomView.showLoading();
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mSymptomView.hideLoading();
        if (response.getRet_values() == null) {
            mSymptomView.getSymptomsFailed("获取症状数据失败，请重试！");
        } else {
            mSymptomView.showSymptoms(response.getRet_values());
        }
    }

    @Override
    protected void onFailed(String message) {
        mSymptomView.hideLoading();
        mSymptomView.getSymptomsFailed(message);
    }
}
