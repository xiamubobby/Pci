package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.RegisterEntity;
import com.wonders.xlab.patient.mvp.model.GetCaptureModel;
import com.wonders.xlab.patient.mvp.model.GetCaptureModelContract;
import com.wonders.xlab.patient.mvp.model.RegisterModel;
import com.wonders.xlab.patient.mvp.model.RegisterModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/4.
 */
public class RegisterPresenter extends BasePresenter implements RegisterPresenterContract.Actions {
    private RegisterPresenterContract.ViewListener mListener;
    private GetCaptureModelContract.Actions mGetCaptureModel;
    private RegisterModelContract.Actions mRegisterModel;

    @Inject
    public RegisterPresenter(RegisterPresenterContract.ViewListener listener, GetCaptureModel getCaptureModel, RegisterModel registerModel) {
        mListener = listener;
        mGetCaptureModel = getCaptureModel;
        mRegisterModel = registerModel;
    }

    @Override
    public void getCapture(String mobile) {
        mGetCaptureModel.getCapture(mobile, new GetCaptureModelContract.Callback() {
            @Override
            public void onGetCaptureSuccess() {
                mListener.showGetCaptureSuccess("获取验证码成功，请稍候...");
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mListener,code,message);
            }
        });
    }

    @Override
    public void register(String mobile, String password, String capture) {
        mRegisterModel.register(mobile, password, capture, new RegisterModelContract.Callback() {
            @Override
            public void onRegisterSuccess(RegisterEntity entity) {
                //TODO 保存注册信息
                mListener.hideLoading();
                mListener.onRegisterSuccess("注册成功");
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mListener,code,message);
            }
        });
    }
}
