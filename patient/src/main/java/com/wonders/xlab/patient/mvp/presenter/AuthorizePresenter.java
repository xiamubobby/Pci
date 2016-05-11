package com.wonders.xlab.patient.mvp.presenter;

import android.text.TextUtils;

import com.wonders.xlab.patient.mvp.model.AuthorizeModel;
import com.wonders.xlab.patient.mvp.model.AuthorizeModelContract;

import java.io.File;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/5.
 */
public class AuthorizePresenter extends BasePresenter implements AuthorizePresenterContract.Actions {
    private AuthorizePresenterContract.ViewListener mViewListener;
    private AuthorizeModelContract.Actions mAuthorizeModel;

    @Inject
    public AuthorizePresenter(AuthorizePresenterContract.ViewListener viewListener, AuthorizeModel authorizeModel) {
        mViewListener = viewListener;
        mAuthorizeModel = authorizeModel;
    }

    @Override
    public void authorize(String patientId, String name, String idNo, File idPic) {
        if (TextUtils.isEmpty(patientId)) {
            mViewListener.showErrorToast("获取用户信息失败，请退出重新登录");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mViewListener.showErrorToast("请先填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idNo)) {
            mViewListener.showErrorToast("请先填写身份证号码");
            return;
        }
        mViewListener.showLoading("正在保存，请稍候...");
        mAuthorizeModel.authorize(patientId, name, idNo, idPic, new AuthorizeModelContract.Callback() {
            @Override
            public void authorizeSuccess(String message) {
                mViewListener.hideLoading();
                mViewListener.authorizeSuccess(message);
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                showError(mViewListener, code, message);
            }
        });
    }
}
