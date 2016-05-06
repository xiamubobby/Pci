package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.XApplication;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.SurgicalHistoryRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.SurgicalHistoryPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 * 住院手术室
 */
public class SurgicalHistoryFragment extends BaseFragment implements SurgicalHistoryPresenterContract.ViewListener {
    private final static String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_surgicalHistory)
    public CommonRecyclerView mRecyclerView;

    private SurgicalHistoryRVAdapter mRVAdapter;

    private SurgicalHistoryPresenterContract.Actions mPresenter;

    public static SurgicalHistoryFragment newInstance(String patientId) {
        SurgicalHistoryFragment fragment = new SurgicalHistoryFragment();
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.surgical_history_fragment, container, false);
        ButterKnife.bind(this, view);

        mPresenter = DaggerSurgicalHistoryComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .surgicalHistoryModule(new SurgicalHistoryModule(this))
                .build()
                .getSurgicalHistoryPresenter();

        addPresenter(mPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getSurgicalHistory(mPatientId, false);
            }
        });
        mPresenter.getSurgicalHistory(mPatientId, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;

    }

    @Override
    public void showSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList) {
        initAdapter();
        mRVAdapter.setDatas(surgicalHistoryBeanList);
    }

    private void initAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new SurgicalHistoryRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
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
                mPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getSurgicalHistory(mPatientId, true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getSurgicalHistory(mPatientId, true);
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
}
