package com.wonders.xlab.patient.mvp.presenter;

import java.io.File;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface AuthorizePresenterContract {
    interface ViewListener extends BasePresenterListener{
        void authorizeSuccess(String message);
    }

    interface Actions extends IBasePresenter{
        void authorize(String patientId, String name, String idNo, File idPic);
    }
}
