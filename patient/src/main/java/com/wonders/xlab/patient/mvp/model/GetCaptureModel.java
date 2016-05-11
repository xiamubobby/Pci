package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.SimpleEntity;

/**
 * Created by hua on 16/5/4.
 */
public class GetCaptureModel extends PatientBaseModel implements GetCaptureModelContract.Actions {
    private AuthAPI mApi;

    @Inject
    public GetCaptureModel(AuthAPI api) {
        mApi = api;
    }

    @Override
    public void getCaptcha(String mobile, final GetCaptureModelContract.Callback callback) {
        request(mApi.getCaptcha(mobile), new Callback<SimpleEntity>() {
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
