package com.wonders.xlab.patient.module.me.userinfo;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import java.io.File;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/11.
 */
public class UserInfoPresenter extends BasePresenter implements UserInfoContract.Presenter, UserInfoContract.Callback {
    @Inject
    AIManager mAIManager;
    private UserInfoContract.ViewListener mViewListener;
    private UserInfoContract.Model mUserInfoModel;

    @Inject
    public UserInfoPresenter(UserInfoContract.ViewListener viewListener, UserInfoModel userInfoModel) {
        mViewListener = viewListener;
        mUserInfoModel = userInfoModel;
        addModel(mUserInfoModel);
    }

    @Override
    public void getUserInfo() {
        mViewListener.showLoading("正在加载,请稍候...");
        mUserInfoModel.getUserInfo(mAIManager.getPatientId(), this);
    }

    @Override
    public void modifyUserInfo(UserInfoBody body) {
        mViewListener.showLoading("正在保存，请稍候...");
        mUserInfoModel.modifyUserInfo(body, mAIManager.getPatientId(), this);
    }

    @Override
    public void uploadAvater(File userAvater) {
        mViewListener.showLoading("图片上传，请稍后...");
        mUserInfoModel.uploadAvater(userAvater, this);

    }

    @Override
    public void modifyUserAvater(String userAvaterUrl) {
        mViewListener.showLoading("正在保存，请稍后...");
        mUserInfoModel.modifyUserAvater(mAIManager.getPatientId(), userAvaterUrl, this);

    }

    @Override
    public void onReceiveUserInfoSuccess(UserInfoEntity entity) {
        mViewListener.hideLoading();
        mViewListener.showUserInfo(entity.getRet_values());
    }

    @Override
    public void onModifyUserInfoSuccess(EmptyValueEntity entity) {
        mViewListener.hideLoading();
        mViewListener.modifyUserInfoSuccess("保存成功!");
    }

    @Override
    public void onUploadUserAvaterSuccess(String url) {
        mViewListener.hideLoading();
        mViewListener.uploadUserAvaterSuccess(url);
    }

    @Override
    public void onModifyUserAvaterSuccess(EmptyValueEntity entity) {
        mViewListener.hideLoading();
        mViewListener.modifyUserAvaterSuccess("保存成功!");
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
