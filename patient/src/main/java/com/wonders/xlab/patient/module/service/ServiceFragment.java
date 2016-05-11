package com.wonders.xlab.patient.module.service;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.alldoctor.AllDoctorActivity;
import com.wonders.xlab.patient.module.service.detail.ServiceDetailActivity;
import com.wonders.xlab.patient.module.service.thirdservice.ThirdServiceActivity;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFragment implements ServicePresenterContract.ViewListener {

    @Bind(R.id.rl_find_doctor)
    RelativeLayout doctor;
    @Bind(R.id.rl_third_service)
    RelativeLayout thirdService;
    @Bind(R.id.recycler)
    CommonRecyclerView recyclerView;
    ServiceRecyclerViewAdapter adapter;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment getInstance() {
        return new ServiceFragment();
    }

    private ServicePresenterContract.Actions servicePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.service_fragment, container, false);
        ButterKnife.bind(this, v);
        servicePresenter = DaggerServiceComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .serviceModule(new ServiceModule(this))
                .build()
                .getServicePresenter();
        addPresenter(servicePresenter);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllDoctorActivity.class));
            }
        });
        thirdService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThirdServiceActivity.class));
            }
        });
        recyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        recyclerView.setRefreshing(true);
        adapter = new ServiceRecyclerViewAdapter();
        adapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getActivity(), ServiceDetailActivity.class);
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
        adapter.appendDatas(list);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {
        recyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        recyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                servicePresenter.getAllServices(true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        recyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                servicePresenter.getAllServices(true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        recyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                servicePresenter.getAllServices(true);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        if (null == recyclerView) {
            return;
        }
        recyclerView.hideRefreshOrLoadMore(true, true);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_service));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_service));
    }
}
