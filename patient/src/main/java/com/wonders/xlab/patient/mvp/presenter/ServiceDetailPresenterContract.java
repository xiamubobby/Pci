package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/5/9.
 */
public interface ServiceDetailPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showServiceDetail();
    }

    interface Actions extends IBasePresenter {
        void getServiceDetail(Long serviceId);
    }
}
