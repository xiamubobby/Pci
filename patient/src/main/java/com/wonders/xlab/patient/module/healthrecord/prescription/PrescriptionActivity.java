package com.wonders.xlab.patient.module.healthrecord.prescription;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.prescription.adapter.PrescriptionRVAdapter;
import com.wonders.xlab.patient.module.healthrecord.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.patient.mvp.presenter.PrescriptionPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/5/10.
 */
public class PrescriptionActivity extends AppbarActivity implements PrescriptionPresenterContract.ViewListener {
    @Bind(R.id.recycler_view_prescription)
    CommonRecyclerView mRecyclerView;

    PrescriptionPresenterContract.Actions mPrescriptionPresenter;

    PrescriptionRVAdapter mRVAdapter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.prescription_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.health_record_prescription);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPatientId = AIManager.getInstance().getPatientId();
        mPrescriptionPresenter = DaggerPrescriptionComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .prescriptionModule(new PrescriptionModule(this))
                .build()
                .getPrescriptionPresenter();
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 10));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPrescriptionPresenter.getPrescriptionList(mPatientId, true);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPrescriptionPresenter.getPrescriptionList(mPatientId, false);
            }
        });
        mPrescriptionPresenter.getPrescriptionList(mPatientId, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mRecyclerView = null;
    }

    private void initAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new PrescriptionRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showPrescriptionList(List<PrescriptionBean> prescriptionBeanList) {
        initAdapter();
        mRVAdapter.setDatas(prescriptionBeanList);
    }

    @Override
    public void appendPrescriptionList(List<PrescriptionBean> prescriptionBeanList) {
        initAdapter();
        mRVAdapter.appendDatas(prescriptionBeanList);
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
                mPrescriptionPresenter.getPrescriptionList(mPatientId, true);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mPrescriptionPresenter.getPrescriptionList(mPatientId, true);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mPrescriptionPresenter.getPrescriptionList(mPatientId, true);
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showErrorToast(String message) {
        showErrorToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}
