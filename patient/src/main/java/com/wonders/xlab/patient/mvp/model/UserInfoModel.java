package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;

/**
 * Created by hua on 16/5/10.
 */
public class UserInfoModel extends PatientBaseModel implements UserInfoModelContract.Actions {
    private UserInfoAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public UserInfoModel(UserInfoAPI api) {
        mAPI = api;
    }

    @Override
    public void getUserInfo(String patientId, final UserInfoModelContract.Callback callback) {
        request(mAPI.getUserInfo(patientId), new Callback<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity response) {
                callback.onReceiveUserInfoSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void modifyUserInfo(UserInfoBody body, String patientId, final UserInfoModelContract.Callback callback) {
        request(mAPI.modifyUserInfo(body,patientId), new Callback<EmptyValueEntity>() {

            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.onModifyUserInfoSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
