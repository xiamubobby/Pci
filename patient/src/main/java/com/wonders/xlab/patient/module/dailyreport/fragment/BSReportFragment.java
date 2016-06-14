package com.wonders.xlab.patient.module.dailyreport.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.dailyreport.adapter.BSReportAdapter;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;
import com.wonders.xlab.patient.mvp.presenter.IBSReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BSReportCachePresenter;
import com.wonders.xlab.patient.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.otto.ShowMeasureChooseDialogOtto;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 今日血糖
 */
public class BSReportFragment extends BaseFragment implements BSReportCachePresenter.BPReportCachePresenterListener {
    private IBSReportPresenter mBSReportPresenter;

    @Bind(R.id.recycler_view_blood_sugar_report)
    CommonRecyclerView mRecyclerView;

    private BSReportAdapter mBSReportAdapter;

    public BSReportFragment() {
        // Required empty public constructor
    }

    public static BSReportFragment newInstance() {
        return new BSReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.bs_report_fragment, container, false);
        ButterKnife.bind(this, view);

        mBSReportPresenter = new BSReportCachePresenter(this);
        addPresenter(mBSReportPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setRefreshEnable(false);

        mBSReportPresenter.getBSCacheList(AIManager.getInstance().getPatientId());
    }

    @Subscribe
    public void refresh(BSSaveSuccessOtto otto) {
        mBSReportPresenter.getBSCacheList(AIManager.getInstance().getPatientId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showBSList(List<BSReportRealmBean> beanList) {
        if (null == mBSReportAdapter) {
            mBSReportAdapter = new BSReportAdapter();
        }
        mRecyclerView.setAdapter(mBSReportAdapter);
        mBSReportAdapter.setDatas(beanList);
    }

    @Override
    public void showEmptyView() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.showEmptyView(null, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
        TextView tvMeasure = (TextView) mRecyclerView.getEmptyView().findViewById(R.id.tv_bp_bs_report_empty_measure);
        tvMeasure.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO umeng
                MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_DAILY_RECORD_BLANK_MEASURE_BS);

                OttoManager.post(new ShowMeasureChooseDialogOtto(ShowMeasureChooseDialogOtto.TYPE_BS));
            }
        });
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
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_daily_report_bs));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_daily_report_bs));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        OttoManager.unregister(this);
    }
}
