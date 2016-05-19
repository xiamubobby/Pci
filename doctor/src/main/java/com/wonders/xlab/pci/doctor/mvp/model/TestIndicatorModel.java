package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.TestIndicatorAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.TestIndicatorEntity;

import javax.inject.Inject;

/**
 * Created by jimmy on 16/5/5.
 */
public class TestIndicatorModel extends DoctorBaseModel<TestIndicatorEntity> implements TestIndicatorModelContract.Actions {

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
        request(mTestIndicatorAPI.getTestIndicatorList(patient, pageIndex), new Callback<TestIndicatorEntity>() {
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
