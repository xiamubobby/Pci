package com.wonders.xlab.patient.module.main.home.dailyreport.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.SymptomReportAdapter;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.main.home.dailyreport.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.presenter.ISymptomReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.SymptomReportPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomReportFragment extends BaseFragment implements SymptomReportPresenter.SymptomReportPresenterListener {

    @Bind(R.id.recycler_view_symptom_report)
    CommonRecyclerView mRecyclerView;

    private SymptomReportAdapter adapter;

    private ISymptomReportPresenter mSymptomReportPresenter;

    public SymptomReportFragment() {
        // Required empty public constructor
    }

    public static SymptomReportFragment newInstance() {
        return new SymptomReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.symptom_report_fragment, container, false);
        ButterKnife.bind(this, view);

        mSymptomReportPresenter = new SymptomReportPresenter(this);
        addPresenter(mSymptomReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 8));

       mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setRefreshing(true);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());
            }
        });
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());
    }

    @Subscribe
    public void refresh(SymptomSaveSuccessOtto otto) {
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showSymptomList(List<SymptomReportBean> reportBeanList) {
        mRecyclerView.showEmptyView();
        if (null == adapter) {
            adapter = new SymptomReportAdapter();
        }
        adapter.setDatas(reportBeanList);
        mRecyclerView.setAdapter(adapter);
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
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }
}
