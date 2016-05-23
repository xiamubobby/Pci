package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.OrderPackageServiceAPI;
import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;
import com.wonders.xlab.patient.mvp.model.IOrderPackageServiceModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/21.
 */
public class OrderPackageServiceModel extends PatientBaseModel<GenerateOrderPaymentEntity> implements IOrderPackageServiceModel {
    private OrderPackageServiceAPI mPackageServiceAPI;
    private OrderPackageServiceModelListener mServiceModelListener;

    public OrderPackageServiceModel(OrderPackageServiceModelListener serviceModelListener) {
        mServiceModelListener = serviceModelListener;

        mPackageServiceAPI = mRetrofit.create(OrderPackageServiceAPI.class);
    }

    @Override
    protected void onSuccess(GenerateOrderPaymentEntity response) {
        mServiceModelListener.onOrderPackageServiceSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mServiceModelListener.onReceiveFailed(code, message);
    }

    @Override
    public void orderPackage(String patientId, String packageId, String paymentChannel) {
        request(mPackageServiceAPI.generateOrderPayment(patientId, packageId, paymentChannel), true);
    }

    public interface OrderPackageServiceModelListener extends BaseModelListener {
        void onOrderPackageServiceSuccess(GenerateOrderPaymentEntity paymentEntity);
    }
}
