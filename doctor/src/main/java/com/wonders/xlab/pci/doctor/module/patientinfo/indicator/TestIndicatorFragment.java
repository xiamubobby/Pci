package com.wonders.xlab.pci.doctor.module.patientinfo.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.XApplication;
import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.adapter.TestIndicatorRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.adapter.bean.TestIndicatorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.TestIndicatorPresenterContract;
import com.wonders.xlab.pci.doctor.mvp.presenter.TestIndicatorPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 */
public class TestIndicatorFragment extends BaseFragment implements TestIndicatorPresenterContract.ViewListener {

    private static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_prescription)
    CommonRecyclerView mRecyclerView;

    private TestIndicatorRVAdapter mRVAdapter;

    private TestIndicatorPresenter mPresenter;

    public static TestIndicatorFragment newInstance(String patientId) {
        TestIndicatorFragment fragment = new TestIndicatorFragment();
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        fragment.setArguments(data);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (null == data) {
            Toast.makeText(getActivity(), "获取检验指标失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mPatientId = data.getString(ARG_PATIENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_indicator_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = DaggerTestIndicatorComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .testIndicatorModule(new TestIndicatorModule(this))
                .build()
                .getTestIndicatorPresenter();
        addPresenter(mPresenter);
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                mPresenter.getTestIndicatorList(mPatientId, false);
            }
        });
        mPresenter.getTestIndicatorList(mPatientId, true);
    }

    @Override
    public void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new TestIndicatorRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(testIndicatorBeanList);
    }

    @Override
    public void appendTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new TestIndicatorRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(testIndicatorBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                mPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getTestIndicatorList(mPatientId, true);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }
}
