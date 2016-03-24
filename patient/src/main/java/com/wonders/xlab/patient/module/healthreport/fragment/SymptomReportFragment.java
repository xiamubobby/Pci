package com.wonders.xlab.patient.module.healthreport.fragment;


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
import com.wonders.xlab.patient.module.healthreport.adapter.SymptomReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.presenter.ISymptomReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.SymptomReportPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.CommonRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomReportFragment extends BaseFragment implements SymptomReportPresenter.SymptomReportPresenterListener {

    @Bind(R.id.recycler_view_symptom_report)
    CommonRecyclerView mRecyclerView;
    @Bind(R.id.refresh_symptom_report)
    SwipeRefreshLayout mRefreshView;

    private SymptomReportAdapter adapter;

    private ISymptomReportPresenter mSymptomReportPresenter;

    public SymptomReportFragment() {
        // Required empty public constructor
    }

    public static SymptomReportFragment newInstance() {
        return new SymptomReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSymptomReportPresenter = new SymptomReportPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.symptom_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 3));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRefreshView.post(new Runnable() {
            @Override
            public void run() {
                mRefreshView.setRefreshing(true);
            }
        });
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomReportPresenter.getSymptomList(AIManager.getInstance(getActivity()).getPatientId());
            }
        });
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Subscribe
    public void refresh(SymptomSaveSuccessOtto otto) {
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showTodaysSymtomList(List<SymptomReportBean> reportBeanList) {
        mRecyclerView.showRecyclerView();
        if (null == adapter) {
            adapter = new SymptomReportAdapter();
        }
        adapter.setDatas(reportBeanList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showEmptyView() {
        mRecyclerView.showEmptyView();
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRefreshView.post(new Runnable() {
            @Override
            public void run() {
                if (null != mRefreshView) {
                    mRefreshView.setRefreshing(false);
                }
            }
        });
    }
}
