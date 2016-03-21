package com.wonders.xlab.patient.module.healthreport.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.healthreport.adapter.BSReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BSReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.presenter.IBSReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BSReportCachePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 今日血糖
 */
public class BSReportFragment extends BaseFragment implements BSReportCachePresenter.BPReportCachePresenterListener {
    private IBSReportPresenter mBSReportPresenter;

    @Bind(R.id.tv_bs_report_ideal_range)
    TextView mTvIdealRange;
    @Bind(R.id.recycler_view_blood_sugar_report)
    RecyclerView mRecyclerView;

    private BSReportAdapter mBSReportAdapter;

    public BSReportFragment() {
        // Required empty public constructor
    }

    public static BSReportFragment newInstance() {
        return new BSReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBSReportPresenter = new BSReportCachePresenter(this);
        addPresenter(mBSReportPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.bs_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));

        mBSReportPresenter.getBSCacheList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Subscribe
    public void refresh(BSSaveSuccessOtto otto) {
        mBSReportPresenter.getBSCacheList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showBPList(List<BSReportBean> beanList) {
        if (null == mBSReportAdapter) {
            mBSReportAdapter = new BSReportAdapter();
        }
        mBSReportAdapter.setDatas(beanList);
        mRecyclerView.setAdapter(mBSReportAdapter);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }
}
