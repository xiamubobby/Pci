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
import com.wonders.xlab.patient.module.healthreport.adapter.BPReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.presenter.IBPReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BPReportCachePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 今日血压
 */
public class BPReportFragment extends BaseFragment implements BPReportCachePresenter.BPReportCachePresenterListener {

    private IBPReportPresenter mBPReportPresenter;

    @Bind(R.id.text_blood_pressure_report_ideal_range)
    TextView mTextIdealRange;
    @Bind(R.id.recycler_view_blood_pressure_report)
    RecyclerView mRecyclerView;

    private BPReportAdapter mBPReportAdapter;

    public BPReportFragment() {
        // Required empty public constructor
    }

    public static BPReportFragment newInstance() {
        return new BPReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBPReportPresenter = new BPReportCachePresenter(this);
        addPresenter(mBPReportPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.bp_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mBPReportPresenter.getBPCacheList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Subscribe
    public void refresh(BPSaveSuccessOtto otto) {
        mBPReportPresenter.getBPCacheList(AIManager.getInstance(getActivity()).getPatientId());
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
        mBPReportAdapter.setDatas(beanList);
        mRecyclerView.setAdapter(mBPReportAdapter);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }
}
