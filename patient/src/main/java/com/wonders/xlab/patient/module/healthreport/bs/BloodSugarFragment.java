package com.wonders.xlab.patient.module.healthreport.bs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
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

public class BloodSugarFragment extends BaseFragment implements BloodSugarPresenter.BloodSugarPresenterListener{

    @Bind(R.id.recycler_view_bs)
    PullLoadMoreRecyclerView mRecyclerView;

    private BSRVAdapter mBSRVAdapter;

    private IBloodSugarPresenter mBSPresenter;
    private String mPatientId;

    public static BloodSugarFragment newInstance() {
        return new BloodSugarFragment();
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
        ButterKnife.bind(this,view);

        mBSPresenter = new BloodSugarPresenter(this);
        addPresenter(mBSPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLinearLayout(false);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mBSPresenter.getBSList(mPatientId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mBSPresenter.getBSList(mPatientId);
    }

    @Override
    public void showBloodPressureList(List<BSBean> bsBeanList) {
        mRecyclerView.setPullLoadMoreCompleted();
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
        }
        mBSRVAdapter.setDatas(bsBeanList);
        mRecyclerView.setAdapter(mBSRVAdapter);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        if (null != mRecyclerView) {
            mRecyclerView.setPullLoadMoreCompleted();
        }
    }
}
