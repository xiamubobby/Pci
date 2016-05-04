package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/4.
 */
public interface LoginPresenterContract {
    interface ViewListener extends BasePresenterListener{
        void loginSuccess(String message);
    }

    interface Actions extends IBasePresenter{
        void login(String tel, String password);
    }
}
