package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.service.detail.ServiceDetailDataUnit;
import com.wonders.xlab.patient.mvp.entity.ServiceContentDetailEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.model.ServiceContentDetailModel;
import com.wonders.xlab.patient.mvp.model.ServiceContentDetailModelContract;
import com.wonders.xlab.patient.mvp.model.ServiceDetailModel;
import com.wonders.xlab.patient.mvp.model.ServiceDetailModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailPresenter extends BasePresenter implements  ServiceDetailPresenterContract.Actions{

    private ServiceDetailPresenterContract.ViewListener mViewListener;
    private ServiceDetailModelContract.Actions mServiceDetailModel;
    private ServiceContentDetailModelContract.Actions mServiceContentDetailModel;

    @Inject
    public ServiceDetailPresenter(ServiceDetailPresenterContract.ViewListener viewListener, ServiceDetailModel serviceDetailModel, ServiceContentDetailModel serviceContentDetailModel) {
        mViewListener = viewListener;
        mServiceDetailModel = serviceDetailModel;
        mServiceContentDetailModel = serviceContentDetailModel;
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
            }

            @Override
            public void showServiceContentDetail(ServiceContentDetailEntity entity) {
                mViewListener.showServiceContentDetail(entity.getRet_values());
            }
        });
    }
}
