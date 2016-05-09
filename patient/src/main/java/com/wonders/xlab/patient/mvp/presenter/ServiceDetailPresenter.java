package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.model.ServiceDetailModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailPresenter extends BasePresenter implements  ServiceDetailPresenterContract.Actions{

    private ServiceDetailPresenterContract.ViewListener mViewListener;
    private ServiceDetailModelContract.Actions mServiceDetailModel;

    @Inject
    public ServiceDetailPresenter(ServiceDetailPresenterContract.ViewListener viewListener,ServiceDetailModelContract.Actions serviceDetailModel) {
        mViewListener = viewListener;
        mServiceDetailModel = serviceDetailModel;
        addModel(serviceDetailModel);
    }

    @Override
    public void getServiceDetail(Long serviceId) {
        mViewListener.showLoading("数据获取中，请稍候...");
        mServiceDetailModel.getServiceDetail(serviceId, new ServiceDetailModelContract.Callback() {
            @Override
            public void showServiceDetail(ServiceDetailEntity entity) {
                mViewListener.hideLoading();
                mViewListener.showServiceDetail(entity);
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                mViewListener.hideLoading();
                mViewListener.showNetworkError(message);
            }
        });
    }
}
