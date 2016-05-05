package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/4.
 */
public interface RegisterPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void getCaptchaSuccess(String message);

        void onRegisterSuccess(String message);
    }

    interface Actions extends IBasePresenter {
        void getCaptcha(String mobile);

        void register(String mobile, String password, String captcha);
    }
}