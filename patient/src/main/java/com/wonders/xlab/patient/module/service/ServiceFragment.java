package com.wonders.xlab.patient.module.service;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.alldoctor.adapter.AllDoctorRVAdapter;
import com.wonders.xlab.patient.module.doctordetail.DoctorDetailActivity;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenterContract;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFragment implements ServicePresenterContract.ViewListener {

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
        recyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                servicePresenter.getAllServices(AIManager.getInstance().getPatientId(), false);
            }
        });
        recyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                servicePresenter.getAllServices(AIManager.getInstance().getPatientId(), true);
            }
        });

        recyclerView.setRefreshing(true);
        adapter = new ServiceRecyclerViewAdapter();
        adapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        servicePresenter.getAllServices(AIManager.getInstance().getPatientId(), true);
    }



    @Override
    public void showAllServiceList(ArrayList<ServiceListCellDataUnit> list) {
        adapter.setDatas(list);
    }

    @Override
    public void appendAllServiceList(ArrayList<ServiceListCellDataUnit> list) {
        adapter.appendDatas(list);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

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
