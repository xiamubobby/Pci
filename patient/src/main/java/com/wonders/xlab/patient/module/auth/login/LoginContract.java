package com.wonders.xlab.patient.module.auth.login;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/16.
 */
public interface LoginContract {
    interface Callback extends BaseModelListener {
        void loginSuccess();
    }

    interface Model extends IBaseModel {
        void login(String tel, String password, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void loginSuccess();
    }

    interface Presenter extends IBasePresenter {
        void login(String tel, String password);
    }
}
