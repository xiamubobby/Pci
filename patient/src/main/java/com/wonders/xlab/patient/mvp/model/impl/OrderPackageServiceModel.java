package com.wonders.xlab.patient.mvp.model.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.OrderPackageServiceAPI;
import com.wonders.xlab.patient.mvp.model.IOrderPackageServiceModel;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/21.
 */
public class OrderPackageServiceModel extends PatientBaseModel<SimpleEntity> implements IOrderPackageServiceModel {
    private OrderPackageServiceAPI mPackageServiceAPI;
    private OrderPackageServiceModelListener mServiceModelListener;

    public OrderPackageServiceModel(OrderPackageServiceModelListener serviceModelListener) {
        mServiceModelListener = serviceModelListener;

        mPackageServiceAPI = mRetrofit.create(OrderPackageServiceAPI.class);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mServiceModelListener.onOrderPackageServiceSuccess("购买成功！");
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        if (TextUtils.isEmpty(message)) {
            mServiceModelListener.onReceiveFailed(e.getMessage());
        } else {
            mServiceModelListener.onReceiveFailed(message);
        }
    }

    @Override
    public void orderPackage(String patientId, String packageId) {
        fetchData(mPackageServiceAPI.orderPackage(patientId,packageId),true);
    }

    public interface OrderPackageServiceModelListener extends BaseModelListener{
        void onOrderPackageServiceSuccess(String message);
    }
}
