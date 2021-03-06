package com.wonders.xlab.patient.module.healthchart.symptom;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.SymptomReportContract;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.di.DaggerSymptomReportComponent;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.di.SymptomReportModule;
import com.wonders.xlab.patient.otto.SymptomSaveSuccessOtto;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;

public class SymptomHRFragment extends BaseFragment implements SymptomReportContract.ViewListener {
    @Bind(R.id.recycler_view_symptom_health_report)
    CommonRecyclerView mRecyclerView;

    private SymptomHRAdapter adapter;

    private SymptomReportContract.Presenter mSymptomReportPresenter;

    public SymptomHRFragment() {
        // Required empty public constructor
    }

    public static SymptomHRFragment newInstance() {
        return new SymptomHRFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.symptom_health_report_fragment, container, false);
        ButterKnife.bind(this, view);

        mSymptomReportPresenter = DaggerSymptomReportComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .symptomReportModule(new SymptomReportModule(this))
                .build()
                .getSymptomReportPresenter();
        addPresenter(mSymptomReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * TODO addItemDecoration必须要在setItemAnimator后调用才有效果，不知道为什么，回头看下源码
         */
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 8));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), 0, Calendar.getInstance().getTimeInMillis(), true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday(), false);
            }
        });
        mRecyclerView.setRefreshing(true);
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), 0, Calendar.getInstance().getTimeInMillis(), true);
    }

    @Subscribe
    public void refresh(SymptomSaveSuccessOtto otto) {
        mSymptomReportPresenter.getSymptomList(AIManager.getInstance().getPatientId(), 0, 0, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showSymptomList(List<SymptomReportBean> reportBeanList) {
        initAdapter();
        adapter.setDatas(reportBeanList);
    }

    @Override
    public void appendSymptomList(List<SymptomReportBean> reportBeanList) {
        initAdapter();
        adapter.appendDatas(reportBeanList);
    }

    private void initAdapter() {
        if (null == adapter) {
            adapter = new SymptomHRAdapter();
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.showEmptyView(null, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void hideLoading() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_symptomhr));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_symptomhr));
    }
}
