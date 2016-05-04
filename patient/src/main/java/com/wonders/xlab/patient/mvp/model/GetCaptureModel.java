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
    private GetCaptureModelContract.ViewListener mListener;

    @Inject
    public GetCaptureModel(GetCaptureModelContract.ViewListener listener, AuthAPI api) {
        mListener = listener;
        mApi = api;
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mListener.onGetCaptureSuccess();
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getCapture(String mobile) {
        request(mApi.getCapture(mobile), true);
    }
}
