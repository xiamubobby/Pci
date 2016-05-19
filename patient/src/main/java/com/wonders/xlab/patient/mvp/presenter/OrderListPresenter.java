package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.order.bean.OrderListBean;
import com.wonders.xlab.patient.mvp.entity.OrderListEntity;
import com.wonders.xlab.patient.mvp.model.OrderListModel;
import com.wonders.xlab.patient.mvp.model.OrderListModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by jimmy on 16/5/9.
 */

public class OrderListPresenter extends BasePagePresenter implements OrderListPresenterContract.Action, OrderListModelContract.Callback {


    private OrderListPresenterContract.viewListener mViewListener;
    private OrderListModelContract.Actions mOrderListModel;

    @Inject
    public OrderListPresenter(OrderListPresenterContract.viewListener orderListPresenter, OrderListModel orderListModel) {
        this.mViewListener = orderListPresenter;
        this.mOrderListModel = orderListModel;
        addModel(mOrderListModel);
    }

    @Override
    public void getOrderList(String patientId, boolean isRefresh) {
        mViewListener.showLoading("");
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.hideLoading();
            mViewListener.showReachTheLastPageNotice("");
            return;
        }
        mOrderListModel.getOrderList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE, this);
    }

    @Override
    public void getOrderListSuccess(OrderListEntity entity) {
        mViewListener.hideLoading();
        OrderListEntity.RetValuesBean retValues = entity.getRet_values();

        updatePageInfo(retValues.getNumber(),retValues.isFirst(),retValues.isLast());

        List<OrderListEntity.RetValuesBean.ContentBean> contentList = retValues.getContent();
        List<OrderListBean> orderListBean = new ArrayList<>();
        for (OrderListEntity.RetValuesBean.ContentBean content : contentList) {
            OrderListBean bean = new OrderListBean();
            bean.setId(content.getId());
            bean.setOrderId(content.getOrderId());
            bean.setOrganizationImageUrl(content.getOrganizationImageUrl());
            bean.setOrganizationName(content.getOrganizationName());
            bean.setSpecificationImageUrl(content.getSpecificationImageUrl());
            bean.setTitle(content.getTitle());
            bean.setPrice(content.getPrice());
            bean.setStatus(content.getStatus());
            orderListBean.add(bean);
        }
        if (shouldAppend()) {
            if (orderListBean.size() == 0) {
                mViewListener.showReachTheLastPageNotice("");
                return;
            }
            mViewListener.appendOrderList(orderListBean);
            return;
        }
        mViewListener.showOrderList(orderListBean);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
