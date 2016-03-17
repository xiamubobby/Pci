package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.UserInfoAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.mvp.model.listener.UserInfoModelListener;

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
    protected void onFailed(Throwable e) {
        mUserInfoModel.onReceiveFailed("获取患者基本信息失败，请重试！");
    }
}
