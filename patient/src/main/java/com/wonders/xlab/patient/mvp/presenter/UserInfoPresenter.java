package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;
import com.wonders.xlab.patient.mvp.model.UserInfoModel;
import com.wonders.xlab.patient.mvp.model.UserInfoModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/11.
 */
public class UserInfoPresenter extends BasePresenter implements UserInfoPresenterContract.Actions, UserInfoModelContract.Callback {
    @Inject
    AIManager mAIManager;
    private UserInfoPresenterContract.ViewListener mViewListener;
    private UserInfoModelContract.Actions mUserInfoModel;

    @Inject
    public UserInfoPresenter(UserInfoPresenterContract.ViewListener viewListener, UserInfoModel userInfoModel) {
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
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
