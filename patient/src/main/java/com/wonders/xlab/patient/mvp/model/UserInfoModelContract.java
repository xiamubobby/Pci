package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/10.
 */
public interface UserInfoModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveUserInfoSuccess(UserInfoEntity entity);

        void onModifyUserInfoSuccess(EmptyValueEntity entity);

        void onReceiveHospitalsSuccess(HospitalAllEntity entity);
    }

    interface Actions extends IBaseModel {
        void getUserInfo(String patientId, Callback callback);

        void modifyUserInfo(UserInfoBody body, String patientId, Callback callback);

        void getAllHospitals(Callback callback);
    }
}
