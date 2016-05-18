package com.wonders.xlab.pci.doctor.module.patientinfo.bloodsugar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodsugar.adapter.BSRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodsugar.bean.BSBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.BSPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.IBSPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSFragment extends BaseFragment implements BSPresenter.BSPresenterListener {
    public static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_bs)
    CommonRecyclerView mRecyclerView;

    private BSRVAdapter mBSRVAdapter;

    private IBSPresenter mBSPresenter;

    public BSFragment() {
        // Required empty public constructor
    }

    public static BSFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        BSFragment fragment = new BSFragment();
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
        View view = inflater.inflate(R.layout.bs_fragment, container, false);
        ButterKnife.bind(this, view);
        mBSPresenter = new BSPresenter(this);
        addPresenter(mBSPresenter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBSPresenter.getBSList(mPatientId,true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBSPresenter.getBSList(mPatientId,false);
            }
        });
        mBSPresenter.getBSList(mPatientId,true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBSRVAdapter = null;
        ButterKnife.unbind(this);
    }

    @Override
    public void showBloodPressureList(List<BSBean> bsBeanList) {
        if (mBSRVAdapter == null) {
            mBSRVAdapter = new BSRVAdapter();
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBSRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(decoration);
            mBSRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    decoration.invalidateHeaders();
                }
            });
            mRecyclerView.setAdapter(mBSRVAdapter);
        }
        mBSRVAdapter.setDatas(bsBeanList);
    }

    @Override
    public void appendBloodPressureList(List<BSBean> bsBeanList) {
        if (mBSRVAdapter == null) {
            mBSRVAdapter = new BSRVAdapter();
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBSRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(decoration);
            mBSRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    decoration.invalidateHeaders();
                }
            });
            mRecyclerView.setAdapter(mBSRVAdapter);
        }
        mBSRVAdapter.appendDatas(bsBeanList);
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
                mBSPresenter.getBSList(mPatientId,true);
            }
        }, im.hua.uikit.R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mBSPresenter.getBSList(mPatientId,true);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mBSPresenter.getBSList(mPatientId,true);
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
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
    public void showReachTheLastPageNotice(String message) {

    }
}
