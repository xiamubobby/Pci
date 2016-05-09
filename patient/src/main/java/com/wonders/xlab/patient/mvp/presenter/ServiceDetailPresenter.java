package com.wonders.xlab.patient.mvp.presenter;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailPresenter extends BasePresenter implements  ServiceDetailPresenterContract.Actions{

    private ServiceDetailPresenterContract.ViewListener mViewListener;

    @Inject
    public ServiceDetailPresenter(ServiceDetailPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void getServiceDetail(Long serviceId) {

        mViewListener.showServiceDetail();
    }
}
