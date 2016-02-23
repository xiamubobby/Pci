package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.UserInfoAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IUserInfoModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoModel extends DoctorBaseModel {
    private IUserInfoModel mUserInfoModel;

    private UserInfoAPI mUserInfoAPI;

    public UserInfoModel(IUserInfoModel userInfoModel) {
        mUserInfoModel = userInfoModel;
        mUserInfoAPI = mRetrofit.create(UserInfoAPI.class);
    }

    public void getUserInfo() {
        fetchData(mUserInfoAPI.getUserInfo(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                if (mUserInfoModel != null) {
                    mUserInfoModel.onReceiveUserInfoSuccess((UserInfoEntity) response);
                }
            }

            @Override
            public void onFailed(Throwable e) {
                if (mUserInfoModel != null) {
                    mUserInfoModel.onReceiveFailed("");
                }
            }
        });
    }
}
