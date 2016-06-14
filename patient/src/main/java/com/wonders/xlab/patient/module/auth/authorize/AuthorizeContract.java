package com.wonders.xlab.patient.module.auth.authorize;

import com.wonders.xlab.patient.mvp.entity.AuthorizeValidateEntity;

import java.io.File;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/6.
 */
public interface AuthorizeContract {
    interface Callback extends BaseModelListener {
        void authorizeSuccess(AuthorizeValidateEntity response);
    }

    interface Model extends IBaseModel {
        void authorize(String patientId, String name, String idNo, File idPic, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showResultMessage(String message);

        void showValidateState(int state);

    }

    interface Presenter extends IBasePresenter {
        void authorize(String patientId, String name, String idNo, File idPic);
    }
}
