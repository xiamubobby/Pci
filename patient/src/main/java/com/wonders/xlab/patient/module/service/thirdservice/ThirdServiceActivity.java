package com.wonders.xlab.patient.module.service.thirdservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.service.DaggerServiceComponent;
import com.wonders.xlab.patient.module.service.ServiceListCellDataUnit;
import com.wonders.xlab.patient.module.service.ServiceModule;
import com.wonders.xlab.patient.module.service.ServiceRecyclerViewAdapter;
import com.wonders.xlab.patient.module.service.detail.ServiceDetailActivity;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by WZH on 16/5/10.
 */
public class ThirdServiceActivity extends AppbarActivity implements ServicePresenterContract.ViewListener {

    @Bind(R.id.recycler_view_third_service)
    CommonRecyclerView recyclerView;
    ServiceRecyclerViewAdapter adapter;

    private ServicePresenterContract.Actions servicePresenter;

    @Override
    public int getContentLayout() {
        return R.layout.third_service_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "服务列表";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        servicePresenter = DaggerServiceComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .serviceModule(new ServiceModule(this))
                .build()
                .getServicePresenter();
        addPresenter(servicePresenter);

        recyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        recyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                servicePresenter.getAllServices(false);
            }
        });
        recyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                servicePresenter.getAllServices(true);
            }
        });

        adapter = new ServiceRecyclerViewAdapter();
        adapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(ThirdServiceActivity.this, ServiceDetailActivity.class);
                i.putExtra(ServiceDetailActivity._key_SERVICE_ID_, adapter.getBean(position).getId());
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
        servicePresenter.getAllServices(true);
    }

    @Override
    public void showAllServiceList(List<ServiceListCellDataUnit> list) {
        adapter.setDatas(list);
    }

    @Override
    public void appendAllServiceList(List<ServiceListCellDataUnit> list) {

    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {
        recyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        recyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                servicePresenter.getAllServices(true);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        recyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                servicePresenter.getAllServices(true);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        recyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {

            }
        },true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        recyclerView.hideRefreshOrLoadMore(true, true);
    }
}
