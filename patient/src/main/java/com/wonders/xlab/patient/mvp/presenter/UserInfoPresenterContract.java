package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.HospitalDicEntity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/11.
 */
public interface UserInfoPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showUserInfo(UserInfoEntity.RetValuesEntity entity);

        void modifyUserInfoSuccess(String message);

        void showHospitalList(List<HospitalDicEntity> dicEntityList);
    }

    interface Actions extends IBasePresenter {
        void getUserInfo();

        void modifyUserInfo(UserInfoBody body);

        void getAllHospitals();
    }
}
