package com.wonders.xlab.patient.module.me.userinfo;

import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import java.io.File;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/10.
 */
public interface UserInfoContract {
    interface Callback extends BaseModelListener {
        void onReceiveUserInfoSuccess(UserInfoEntity entity);

        void onModifyUserInfoSuccess(EmptyValueEntity entity);

        void onUploadUserAvaterSuccess(String url);

        void onModifyUserAvaterSuccess(EmptyValueEntity entity);
    }

    interface Model extends IBaseModel {
        void getUserInfo(String patientId, Callback callback);

        void modifyUserInfo(UserInfoBody body, String patientId, Callback callback);

        void uploadAvater(File userAvater, Callback callback);

        void modifyUserAvater(String patientId, String userAvaterUrl, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showUserInfo(UserInfoEntity.RetValuesEntity entity);

        void modifyUserInfoSuccess(String message);

        void uploadUserAvaterSuccess(String url);

        void modifyUserAvaterSuccess(String message);
    }

    interface Presenter extends IBasePresenter {
        void getUserInfo();

        void modifyUserInfo(UserInfoBody body);

        void uploadAvater(File userAvater);

        void modifyUserAvater(String userAvaterUrl);
    }
}
