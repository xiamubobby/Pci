package com.wonders.xlab.pci.module.record.monitor.mvn.model;

import com.wonders.xlab.pci.module.record.monitor.mvn.api.SymptomAPI;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.SymptomEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.SymptomView;
import com.wonders.xlab.pci.mvn.model.BaseModel;

/**
 * Created by hua on 15/12/22.
 */
public class SymptomModel extends BaseModel<SymptomEntity> {
    private SymptomView mSymptomView;
    private SymptomAPI mSymptomAPI;

    public SymptomModel(SymptomView symptomView) {
        mSymptomView = symptomView;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms(String userId,long date) {
        setObservable(mSymptomAPI.getSymptoms(userId,date));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSymptomView.showLoading();
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mSymptomView.hideLoading();
        SymptomEntity.RetValuesEntity ret_values = response.getRet_values();
        if (ret_values == null) {
            mSymptomView.onFailed("获取数据出错，请重试！");
            return;
        }
        mSymptomView.showSymptomLabels(ret_values.getSymptomList());
        mSymptomView.showSymptomAdvices(ret_values.getUserSymptomAdvices());
    }

    @Override
    protected void onFailed(String message) {
        mSymptomView.hideLoading();
        mSymptomView.onFailed(message);
    }
}
