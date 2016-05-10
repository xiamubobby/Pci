package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.service.detail.ServiceDetailDataUnit;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/5/9.
 */
public interface ServiceDetailPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showServiceDetail(ServiceDetailDataUnit dataUnit);
    }

    interface Actions extends IBasePresenter {
        void getServiceDetail(Long serviceId);
    }

}
