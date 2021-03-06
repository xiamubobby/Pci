package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.service.detail.ServiceDetailDataUnit;
import com.wonders.xlab.patient.mvp.entity.ServiceContentDetailEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceOrderEntity;
import com.wonders.xlab.patient.mvp.model.ServiceContentDetailModel;
import com.wonders.xlab.patient.mvp.model.ServiceContentDetailModelContract;
import com.wonders.xlab.patient.mvp.model.ServiceDetailModel;
import com.wonders.xlab.patient.mvp.model.ServiceDetailModelContract;
import com.wonders.xlab.patient.mvp.model.ServiceOrderModelContract;
import com.wonders.xlab.patient.mvp.model.ServiceOrderModel;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailPresenter extends BasePresenter implements ServiceDetailPresenterContract.Actions {

    private ServiceDetailPresenterContract.ViewListener mViewListener;
    private ServiceDetailModelContract.Actions mServiceDetailModel;
    private ServiceContentDetailModelContract.Actions mServiceContentDetailModel;
    private ServiceOrderModelContract.Actions mServiceOrderModel;

    @Inject
    public ServiceDetailPresenter(ServiceDetailPresenterContract.ViewListener viewListener, ServiceDetailModel serviceDetailModel, ServiceContentDetailModel serviceContentDetailModel, ServiceOrderModel ServiceOrderModel) {
        mViewListener = viewListener;
        mServiceDetailModel = serviceDetailModel;
        mServiceContentDetailModel = serviceContentDetailModel;
        mServiceOrderModel = ServiceOrderModel;
        addModel(serviceDetailModel);
    }

    @Override
    public void getServiceDetail(Long serviceId) {
        mViewListener.showLoading("数据获取中，请稍候...");
        mServiceDetailModel.getServiceDetail(serviceId, new ServiceDetailModelContract.Callback() {
            @Override
            public void showServiceDetail(ServiceDetailEntity entity) {
                mViewListener.hideLoading();
                mViewListener.showServiceDetail(new ServiceDetailDataUnit(entity));
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                mViewListener.hideLoading();
                mViewListener.showNetworkError(message);
            }
        });
    }

    @Override
    public void getServiceContentDetail(Long serviceId) {
        mServiceContentDetailModel.getServiceContentDetail(serviceId, new ServiceContentDetailModelContract.Callback() {

            @Override
            public void onReceiveFailed(int code, String message) {
                mViewListener.hideLoading();
                mViewListener.showNetworkError(message);
            }

            @Override
            public void showServiceContentDetail(ServiceContentDetailEntity entity) {
                mViewListener.hideLoading();
                mViewListener.showServiceContentDetail(entity.getRet_values());

            }
        });
    }

    @Override
    public void orderService(Long serviceId) {
        mViewListener.showLoading("购买中请稍候...");
        mServiceOrderModel.orderService(serviceId, new ServiceOrderModelContract.Callback() {
            @Override
            public void onOrderSuccessed(ServiceOrderEntity entity) {
                mViewListener.hideLoading();
                mViewListener.onServiceSuccess();
            }

            @Override
            public void onReceiveFailed(int code, String message) {
                mViewListener.hideLoading();
                mViewListener.onServiceFail(message);
                mViewListener.showNetworkError(message);
            }
        });
    }
}
