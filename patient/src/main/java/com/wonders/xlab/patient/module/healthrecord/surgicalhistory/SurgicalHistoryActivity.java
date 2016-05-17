package com.wonders.xlab.patient.module.healthrecord.surgicalhistory;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.adapter.SurgicalHistoryRVAdapter;
import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.patient.mvp.presenter.SurgicalHistoryPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/5/10.
 */
public class SurgicalHistoryActivity extends AppbarActivity implements SurgicalHistoryPresenterContract.ViewListener {
    @Bind(R.id.recycler_view_surgicalHistory)
    CommonRecyclerView mRecyclerView;

    SurgicalHistoryPresenterContract.Actions mSurgicalHistoryPresenter;

    SurgicalHistoryRVAdapter mRVAdapter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.surgical_history_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.health_record_surgical_history);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mSurgicalHistoryPresenter = DaggerSurgicalHistoryComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .surgicalHistoryModule(new SurgicalHistoryModule(this))
                .build()
                .getSurgicalHistoryPresenter();
        mPatientId = AIManager.getInstance().getPatientId();
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, false);
            }
        });
        mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, true);

    }

    private void initAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new SurgicalHistoryRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList) {
        initAdapter();
        mRVAdapter.setDatas(surgicalHistoryBeanList);
    }

    @Override
    public void appendSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList) {
        initAdapter();
        mRVAdapter.appendDatas(surgicalHistoryBeanList);
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
                mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mSurgicalHistoryPresenter.getSurgicalHistory(mPatientId, true);
            }
        }, true);
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
    }
}
