package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.OrderListApi;
import com.wonders.xlab.patient.mvp.entity.OrderListEntity;

import javax.inject.Inject;

/**
 * Created by jimmy on 16/5/9.
 */
public class OrderListModal extends PatientBaseModel implements OrderListModalContract.Actions {

    private OrderListApi mOrderListAPI;

    @Inject
    public OrderListModal(OrderListApi orderListAPI) {
        this.mOrderListAPI = orderListAPI;
    }

    @Override
    public void getOrderList(String patientId, int page, int size, final OrderListModalContract.Callback callback) {
        request(mOrderListAPI.getOrderList(patientId,page,size), new Callback<OrderListEntity>() {
            @Override
            public void onSuccess(OrderListEntity response) {
                callback.getOrderListSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
