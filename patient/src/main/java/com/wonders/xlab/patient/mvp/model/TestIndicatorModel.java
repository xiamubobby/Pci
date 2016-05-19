package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.TestIndicatorAPI;
import com.wonders.xlab.patient.mvp.entity.TestIndicatorEntity;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by jimmy on 16/5/5.
 */
public class TestIndicatorModel extends PatientBaseModel implements TestIndicatorModelContract.Actions {

    private TestIndicatorAPI mTestIndicatorAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public TestIndicatorModel(TestIndicatorAPI testIndicatorAPI) {
        mTestIndicatorAPI = testIndicatorAPI;
    }

    @Override
    public void getTestIndicatorList(String patient, int pageIndex, final TestIndicatorModelContract.Callback callback) {
        request(mTestIndicatorAPI.getTestIndicatorList(patient, pageIndex), new BaseModel.Callback<TestIndicatorEntity>() {
            @Override
            public void onSuccess(TestIndicatorEntity response) {
                callback.getTestIndicatorListSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
