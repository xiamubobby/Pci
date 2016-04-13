package com.wonders.xlab.pci.doctor.module.chatroom.bs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.adapter.BSRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.bean.BSBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.BSPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BloodSugarActivity extends AppbarActivity implements BSPresenter.BSPresenterListener {
    public static final String EXTRA_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_bs)
    PullLoadMoreRecyclerView mRecyclerView;

    private BSRVAdapter mBSRVAdapter;

    private BSPresenter mBSPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.bs_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取患者血糖信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取患者血糖信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        mBSPresenter = new BSPresenter(this);
        addPresenter(mBSPresenter);

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
    public void showNetworkError(String message) {
        mRecyclerView.setPullLoadMoreCompleted();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void hideLoading() {

    }
}
