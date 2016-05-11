package com.wonders.xlab.patient.module.healthrecord.testindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.testindicator.adapter.TestIndicatorRVAdapter;
import com.wonders.xlab.patient.module.healthrecord.testindicator.adapter.bean.TestIndicatorBean;
import com.wonders.xlab.patient.mvp.presenter.TestIndicatorPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/5/10.
 */
public class TestIndicatorActivity extends AppbarActivity implements TestIndicatorPresenterContract.ViewListener {


    @Bind(R.id.recycler_view_test_indicator)
    CommonRecyclerView mRecyclerView;

    private TestIndicatorPresenterContract.Actions mTestIndicatorPresenter;

    private TestIndicatorRVAdapter mRVAdapter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.test_indicator_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.health_record_test_indicator);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTestIndicatorPresenter = DaggerTestIndicatorComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .testIndicatorModule(new TestIndicatorModule(this))
                .build()
                .getTestIndicatorPresenter();
        addPresenter(mTestIndicatorPresenter);
        mPatientId = AIManager.getInstance().getPatientId();

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 10));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mTestIndicatorPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTestIndicatorPresenter.getTestIndicatorList(mPatientId, false);
            }
        });
        mTestIndicatorPresenter.getTestIndicatorList(mPatientId, true);
    }

    private void initAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new TestIndicatorRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList) {
        initAdapter();
        mRVAdapter.setDatas(testIndicatorBeanList);
    }

    @Override
    public void appendTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList) {
        initAdapter();
        mRVAdapter.appendDatas(testIndicatorBeanList);
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
                mTestIndicatorPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mTestIndicatorPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mTestIndicatorPresenter.getTestIndicatorList(mPatientId, true);
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
