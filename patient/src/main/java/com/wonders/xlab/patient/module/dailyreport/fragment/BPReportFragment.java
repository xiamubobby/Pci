package com.wonders.xlab.patient.module.dailyreport.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.dailyreport.adapter.BPReportAdapter;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.module.dailyreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.module.dailyreport.otto.ShowMeasureChooseDialogOtto;
import com.wonders.xlab.patient.mvp.presenter.IBPReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BPReportCachePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 今日血压
 */
public class BPReportFragment extends BaseFragment implements BPReportCachePresenter.BPReportCachePresenterListener {

    private IBPReportPresenter mBPReportPresenter;

    @Bind(R.id.recycler_view_blood_pressure_report)
    CommonRecyclerView mRecyclerView;

    private BPReportAdapter mBPReportAdapter;

    public BPReportFragment() {
        // Required empty public constructor
    }

    public static BPReportFragment newInstance() {
        return new BPReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.bp_report_fragment, container, false);
        ButterKnife.bind(this, view);

        mBPReportPresenter = new BPReportCachePresenter(this);
        addPresenter(mBPReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.showEmptyView();
        TextView tvMeasure = (TextView) mRecyclerView.findViewById(R.id.tv_bp_bs_report_empty_measure);
        tvMeasure.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OttoManager.post(new ShowMeasureChooseDialogOtto(ShowMeasureChooseDialogOtto.TYPE_BP));
            }
        });

        mBPReportPresenter.getBPCacheList(AIManager.getInstance().getPatientId());
    }

    @Subscribe
    public void refresh(BPSaveSuccessOtto otto) {
        mBPReportPresenter.getBPCacheList(AIManager.getInstance().getPatientId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showBPList(List<BPReportBean> beanList) {
        if (null == mBPReportAdapter) {
            mBPReportAdapter = new BPReportAdapter();
        }
        mRecyclerView.setAdapter(mBPReportAdapter);
        mBPReportAdapter.setDatas(beanList);
    }

    @Override
    public void showEmptyView() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.showEmptyView();
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }
}
