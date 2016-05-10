package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.order.bean.OrderListBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/9.
 */
public interface OrderListPresenterContract {

    interface viewListener extends BasePagePresenterListener {
        void showOrderList(List<OrderListBean> beanList);
        void appendOrderList(List<OrderListBean> beanList);
    }

    interface Action extends IBasePresenter {
        void getOrderList(String patientId, boolean isRefresh);
    }
}
