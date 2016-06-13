package com.wonders.xlab.patient.module.auth.authorize;

import android.text.TextUtils;

import com.wonders.xlab.patient.mvp.entity.AuthorizeValidateEntity;

import java.io.File;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/5.
 */
public class AuthorizePresenter extends BasePresenter implements AuthorizeContract.Presenter {
    private AuthorizeContract.ViewListener mViewListener;
    private AuthorizeContract.Model mAuthorizeModel;

    @Inject
    public AuthorizePresenter(AuthorizeContract.ViewListener viewListener, AuthorizeModel authorizeModel) {
        mViewListener = viewListener;
        mAuthorizeModel = authorizeModel;
    }

    @Override
    public void authorize(String patientId, String name, String idNo, File idPic) {
        if (TextUtils.isEmpty(patientId)) {
            mViewListener.showToast("获取用户信息失败，请退出重新登录");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mViewListener.showToast("请先填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idNo)) {
            mViewListener.showToast("请先填写身份证号码");
            return;
        }
        mViewListener.showLoading("正在保存，请稍候...");
        mAuthorizeModel.authorize(patientId, name, idNo, idPic, new AuthorizeContract.Callback() {
            @Override
            public void authorizeSuccess(AuthorizeValidateEntity entity) {
                mViewListener.hideLoading();
                mViewListener.showResultMessage(entity.getMessage());
                mViewListener.showValidateState(entity.getRet_values());
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }
        });
    }
}
