package com.wonders.xlab.patient.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.order.adapter.OrderListAdapter;
import com.wonders.xlab.patient.module.order.bean.OrderListBean;
import com.wonders.xlab.patient.mvp.presenter.OrderListPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/5/6.
 */
public class OrderListActivity extends AppbarActivity implements OrderListPresenterContract.viewListener {


    @Bind(R.id.recycler_view_order_list)
    CommonRecyclerView mRecyclerView;

    private OrderListPresenterContract.Action mOrderListPresenter;

    private OrderListAdapter mRVAdapter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.order_list_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_activity_my_order);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mOrderListPresenter = DaggerOrderListComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .orderListModule(new OrderListModule(this))
                .build()
                .getOrderListPresenter();
        addPresenter(mOrderListPresenter);

        mPatientId = AIManager.getInstance().getPatientId();
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mOrderListPresenter.getOrderList(mPatientId, true);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mOrderListPresenter.getOrderList(mPatientId, false);
            }
        });
        mOrderListPresenter.getOrderList(mPatientId, true);

    }

    private void initAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new OrderListAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showOrderList(List<OrderListBean> beanList) {
        initAdapter();
        mRVAdapter.setDatas(beanList);
    }

    @Override
    public void appendOrderList(List<OrderListBean> beanList) {
        initAdapter();
        mRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mOrderListPresenter.getOrderList(mPatientId, true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mOrderListPresenter.getOrderList(mPatientId, true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mOrderListPresenter.getOrderList(mPatientId, true);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }
}
