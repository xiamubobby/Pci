package com.wonders.xlab.patient.module.auth.register;

import android.text.TextUtils;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.RegisterEntity;
import com.wonders.xlab.patient.mvp.model.GetCaptureModel;
import com.wonders.xlab.patient.mvp.model.GetCaptureModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/4.
 */
public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter {
    private RegisterContract.ViewListener mListener;
    private GetCaptureModelContract.Actions mGetCaptureModel;
    private RegisterContract.Model mRegisterModel;

    @Inject
    public RegisterPresenter(RegisterContract.ViewListener listener, GetCaptureModel getCaptureModel, RegisterModel registerModel) {
        mListener = listener;
        mGetCaptureModel = getCaptureModel;
        mRegisterModel = registerModel;
    }

    @Override
    public void getCaptcha(String mobile) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            mListener.showToast("请填写11位的手机号！");
            return;
        }
        mGetCaptureModel.getCaptcha(mobile, new GetCaptureModelContract.Callback() {
            @Override
            public void onGetCaptureSuccess() {
                mListener.getCaptchaSuccess("获取验证码成功，请稍候...");
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mListener, code, message);
            }
        });
    }

    @Override
    public void register(String mobile, String password, String captcha) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            mListener.showToast("请填写11位的手机号！");
            return;
        }
        if (TextUtils.isEmpty(captcha) || captcha.length() != 6) {
            mListener.showToast("请填写6位的数字验证码！");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mListener.showToast("密码长度必须大于等于6！");
            return;
        }

        mListener.showLoading("正在注册，请稍候...");
        mRegisterModel.register(mobile, password, captcha, new RegisterContract.Callback() {
            @Override
            public void onRegisterSuccess(RegisterEntity entity) {
                //TODO 保存注册信息
                RegisterEntity.RetValuesEntity retValues = entity.getRet_values();
                AIManager.getInstance().savePatientInfo(retValues.getId(), retValues.getTel(), retValues.getAvatarUrl(), retValues.getName(), "", "");
                mListener.hideLoading();
                mListener.onRegisterSuccess("注册成功");
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mListener, code, message);
            }
        });
    }
}
