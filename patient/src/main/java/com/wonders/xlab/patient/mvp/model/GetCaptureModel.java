package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.SimpleEntity;

/**
 * Created by hua on 16/5/4.
 */
public class GetCaptureModel extends PatientBaseModel<SimpleEntity> implements GetCaptureModelContract.Actions {
    private AuthAPI mApi;

    @Inject
    public GetCaptureModel(AuthAPI api) {
        mApi = api;
    }

    @Override
    public void getCapture(String mobile, final GetCaptureModelContract.Callback callback) {
        request(mApi.getCapture(mobile), new Callback<SimpleEntity>() {
            @Override
            public void onSuccess(SimpleEntity response) {
                callback.onGetCaptureSuccess();
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
