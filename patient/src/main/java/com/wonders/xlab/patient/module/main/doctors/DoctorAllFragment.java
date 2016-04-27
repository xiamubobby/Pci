package com.wonders.xlab.patient.module.main.doctors;


import android.content.Intent;
import android.os.Bundle;
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
import com.wonders.xlab.patient.di.DaggerPresenterComponent;
import com.wonders.xlab.patient.di.PresenterModule;
import com.wonders.xlab.patient.module.main.doctors.adapter.AllDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.module.main.doctors.detail.DoctorDetailActivity;
import com.wonders.xlab.patient.mvp.presenter.IDoctorAllPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorAllPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 所有医生
 */
public class DoctorAllFragment extends BaseFragment implements DoctorAllPresenter.DoctorAllPresenterListener {

    @Bind(R.id.recycler_view_doctor_all)
    CommonRecyclerView mRecyclerView;

    private IDoctorAllPresenter mDoctorAllPresenter;

    private AllDoctorRVAdapter mAllDoctorRVAdapter;

    public DoctorAllFragment() {
        // Required empty public constructor
    }

    public static DoctorAllFragment newInstance() {
        return new DoctorAllFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.doctor_all_fragment, container, false);
        ButterKnife.bind(this, view);

        mDoctorAllPresenter = DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .getDoctorAllPresenter();
        addPresenter(mDoctorAllPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), true);
            }
        });

        mRecyclerView.setRefreshing(true);
        mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAllDoctorRVAdapter = null;
        ButterKnife.unbind(this);
    }

    private void initRecyclerViewAdapter() {
        if (null == mAllDoctorRVAdapter) {
            mAllDoctorRVAdapter = new AllDoctorRVAdapter();
            mAllDoctorRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
                    intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, mAllDoctorRVAdapter.getBean(position).getDoctorGroupName());
                    intent.putExtra(DoctorDetailActivity.EXTRA_ID, mAllDoctorRVAdapter.getBean(position).getOwnerId());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(mAllDoctorRVAdapter);
        }
    }

    @Override
    public void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        initRecyclerViewAdapter();
        mAllDoctorRVAdapter.setDatas(myDoctorBeanList);
    }

    @Override
    public void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        initRecyclerViewAdapter();
        mAllDoctorRVAdapter.appendDatas(myDoctorBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), true);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
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
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance().getPatientId(), true);
            }
        });
    }

    @Override
    public void hideLoading() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_doctors_all));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_doctors_all));
    }
}
