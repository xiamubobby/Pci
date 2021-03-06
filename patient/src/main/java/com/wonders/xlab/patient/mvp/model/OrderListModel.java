package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.OrderListApi;
import com.wonders.xlab.patient.mvp.entity.OrderListEntity;

import javax.inject.Inject;

/**
 * Created by jimmy on 16/5/9.
 */
public class OrderListModel extends PatientBaseModel implements OrderListModelContract.Actions {

    private OrderListApi mOrderListAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public OrderListModel(OrderListApi orderListAPI) {
        this.mOrderListAPI = orderListAPI;
    }

    @Override
    public void getOrderList(String patientId, int page, int size, final OrderListModelContract.Callback callback) {
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
