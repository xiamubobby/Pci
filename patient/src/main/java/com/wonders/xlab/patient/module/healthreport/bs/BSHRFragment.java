package com.wonders.xlab.patient.module.healthreport.bs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.healthreport.bs.adapter.BSRVAdapter;
import com.wonders.xlab.patient.module.healthreport.bs.bean.BSBean;
import com.wonders.xlab.patient.mvp.presenter.IBloodSugarPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BloodSugarPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

public class BSHRFragment extends BaseFragment implements BloodSugarPresenter.BloodSugarPresenterListener {

    @Bind(R.id.recycler_view_bs)
    CommonRecyclerView mRecyclerView;

    private BSRVAdapter mBSRVAdapter;

    private IBloodSugarPresenter mBSPresenter;
    private String mPatientId;

    public static BSHRFragment newInstance() {
        return new BSHRFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatientId = AIManager.getInstance().getPatientId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_hr_fragment, container, false);
        ButterKnife.bind(this, view);

        mBSPresenter = new BloodSugarPresenter(this);
        addPresenter(mBSPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBSPresenter.getBSList(mPatientId, false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBSPresenter.getBSList(mPatientId, true);
            }
        });

        mRecyclerView.setRefreshing(true);
        mBSPresenter.getBSList(mPatientId, true);
    }

    @Override
    public void showBloodPressureList(List<BSBean> bsBeanList) {
        initAdapter();
        mBSRVAdapter.setDatas(bsBeanList);
    }

    @Override
    public void appendBloodPressureList(List<BSBean> bsBeanList) {
        initAdapter();
        mBSRVAdapter.appendDatas(bsBeanList);
    }

    private void initAdapter() {
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
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBSRVAdapter = null;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bshr));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bshr));
    }
}
