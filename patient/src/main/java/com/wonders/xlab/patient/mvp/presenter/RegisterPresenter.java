package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.model.GetCaptureModelContract;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/4.
 */
public class RegisterPresenter extends BasePresenter implements RegisterPresenterContract.Actions {
    private RegisterPresenterContract.ViewListener mListener;
    private GetCaptureModelContract.Actions mGetCaptureActions;

    @Override
    public void getCapture(String mobile) {

    }

    @Override
    public void register(String mobile, String capture, String password) {

    }
}
