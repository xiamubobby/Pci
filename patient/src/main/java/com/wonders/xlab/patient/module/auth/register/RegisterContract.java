package com.wonders.xlab.patient.module.auth.register;

import com.wonders.xlab.patient.mvp.entity.RegisterEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/16.
 */
public interface RegisterContract {
    interface Callback extends BaseModelListener {
        void onRegisterSuccess(RegisterEntity entity);
    }

    interface Model extends IBaseModel {
        void register(String tel,String password,String capture,Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void getCaptchaSuccess(String message);

        void onRegisterSuccess(String message);
    }

    interface Presenter extends IBasePresenter {
        void getCaptcha(String mobile);

        void register(String mobile, String password, String captcha);
    }
}
