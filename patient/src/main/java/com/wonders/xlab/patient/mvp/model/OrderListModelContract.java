package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.OrderListEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by jimmy on 16/5/9.
 */
public interface OrderListModelContract {

    interface Callback extends BaseModelListener {
        void getOrderListSuccess(OrderListEntity entity);
    }

    interface Actions extends IBaseModel {
        void getOrderList(String patientId, int page, int size, Callback callback);
    }
}
