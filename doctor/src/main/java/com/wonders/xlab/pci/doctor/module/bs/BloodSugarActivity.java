package com.wonders.xlab.pci.doctor.module.bs;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.bs.adapter.BSRVAdapter;
import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.BSPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IBSPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BloodSugarActivity extends AppbarActivity implements IBSPresenter{

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

        mRecyclerView.setLinearLayout();

        mBSPresenter = new BSPresenter(this);
        addPresenter(mBSPresenter);

        mBSPresenter.getBSList();
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
        }
        mBSRVAdapter.setDatas(bsBeanList);
        mRecyclerView.setAdapter(mBSRVAdapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
