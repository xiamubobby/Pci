package com.wonders.xlab.patient.module.me.userinfo;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import java.io.File;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.impl.BaseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hua on 16/5/10.
 */
public class UserInfoModel extends PatientBaseModel implements UserInfoContract.Model {
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
    public void getUserInfo(String patientId, final UserInfoContract.Callback callback) {
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
    public void modifyUserInfo(UserInfoBody body, String patientId, final UserInfoContract.Callback callback) {
        request(mAPI.modifyUserInfo(body, patientId), new Callback<EmptyValueEntity>() {

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

    @Override
    public void uploadAvater(File userAvater, final UserInfoContract.Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (userAvater != null && userAvater.exists()) {
            builder.addFormDataPart("file", userAvater.getName(), RequestBody.create(MediaType.parse("image/*"), userAvater));
        } else {
            callback.onReceiveFailed(BaseModel.ERROR_CODE_CLIENT_EXCEPTION, "请先选择照片");
            return;
        }
        request(mAPI.uploadUserAvater(builder.build()), new Callback<SimpleEntity>() {
            @Override
            public void onSuccess(SimpleEntity response) {
                callback.onUploadUserAvaterSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void modifyUserAvater(String patientId, String userAvaterUrl, final UserInfoContract.Callback callback) {
        request(mAPI.modifyUserAvater(patientId, userAvaterUrl), new Callback<EmptyValueEntity>() {
            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.onModifyUserAvaterSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
