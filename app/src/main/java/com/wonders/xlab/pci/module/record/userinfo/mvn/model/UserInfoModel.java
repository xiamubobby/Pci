package com.wonders.xlab.pci.module.record.userinfo.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.record.userinfo.mvn.api.UserInfoAPI;
import com.wonders.xlab.pci.module.record.userinfo.mvn.entity.UserInfoEntity;
import com.wonders.xlab.pci.module.record.userinfo.mvn.view.UserInfoView;

/**
 * Created by hua on 15/12/17.
 */
public class UserInfoModel extends BaseModel<UserInfoEntity> {
    private UserInfoView mUserInfoView;
    private UserInfoAPI mUserInfoAPI;

    public UserInfoModel(UserInfoView userInfoView) {
        mUserInfoView = userInfoView;
        mUserInfoAPI = mRetrofit.create(UserInfoAPI.class);
    }

    public void getUserInfo(String userId) {
        setObservable(mUserInfoAPI.getUserInfo(userId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserInfoView.showLoading();
    }

    @Override
    protected void onSuccess(@NonNull UserInfoEntity response) {
        mUserInfoView.hideLoading();
        if (response.getRet_values() == null) {
            mUserInfoView.onFailed("获取个人信息失败，请重试！");
        } else {
            mUserInfoView.onSuccess(response.getRet_values());
        }
    }

    @Override
    protected void onFailed(String message) {
        mUserInfoView.hideLoading();
        mUserInfoView.onFailed(message);
    }
}
