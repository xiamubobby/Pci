package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.UserInfoAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoModel extends DoctorBaseModel<UserInfoEntity> {
    private UserInfoModelListener mUserInfoModel;

    private UserInfoAPI mUserInfoAPI;

    public UserInfoModel(UserInfoModelListener userInfoModel) {
        mUserInfoModel = userInfoModel;
        mUserInfoAPI = mRetrofit.create(UserInfoAPI.class);
    }

    public void getUserInfo(String userId) {
        fetchData(mUserInfoAPI.getUserInfo(userId), true);
    }

    @Override
    protected void onSuccess(UserInfoEntity response) {
        mUserInfoModel.onReceiveUserInfoSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mUserInfoModel.onReceiveFailed(code, "获取患者基本信息失败，请重试！");
    }

    public interface UserInfoModelListener extends BaseModelListener {
        void onReceiveUserInfoSuccess(UserInfoEntity entity);
    }
}
