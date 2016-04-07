package com.wonders.xlab.patient.module.healthreport.symptom;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.presenter.ISymptomReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.SymptomReportPresenter;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DateUtil;

public class SymptomHRFragment extends BaseFragment implements SymptomReportPresenter.SymptomReportPresenterListener {
    @Bind(R.id.recycler_view_symptom_health_report)
    CommonRecyclerView mRecyclerView;

    private SymptomHRAdapter adapter;

    private ISymptomReportPresenter mSymptomReportPresenter;

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

        mSymptomReportPresenter = new SymptomReportPresenter(this);
        addPresenter(mSymptomReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * TODO addItemDecoration必须要在setItemAnimator后调用才有效果，不知道为什么，回头看下源码
         */
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_symptomhr));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_symptomhr));
    }
}
