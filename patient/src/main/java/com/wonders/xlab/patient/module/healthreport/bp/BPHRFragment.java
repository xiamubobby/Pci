package com.wonders.xlab.patient.module.healthreport.bp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.healthreport.bp.adapter.BPRVAdapter;
import com.wonders.xlab.patient.module.healthreport.bp.bean.BPListBean;
import com.wonders.xlab.patient.mvp.presenter.IBloodPressurePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BloodPressurePresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

public class BPHRFragment extends BaseFragment implements BloodPressurePresenter.BloodPressurePresenterListener {
    @Bind(R.id.chart_blood_pressure)
    CombinedChart mChart;

    private IBloodPressurePresenter mBPPresenter;
    private BPRVAdapter mBPRVAdapter;

    private final int itemMaxX = 30;

    @Bind(R.id.recycler_view_blood_pressure)
    CommonRecyclerView mRecyclerView;

    public static BPHRFragment newInstance() {
        return new BPHRFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bp_hr_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChart();

        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBPPresenter.getBPList(AIManager.getInstance().getPatientId(), false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBPPresenter.getBPList(AIManager.getInstance().getPatientId(), true);
            }
        });

        mBPPresenter = new BloodPressurePresenter(this);
        addPresenter(mBPPresenter);

        mRecyclerView.setRefreshing(true);
        mBPPresenter.getBPList(AIManager.getInstance().getPatientId(), true);
    }

    private void initChart() {
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDescription("血压(mmHg)");
        mChart.setNoDataText("暂无数据");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(true);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        addLimitLines(leftAxis);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        BPMarkerView mv = new BPMarkerView(this, R.layout.custom_marker_view);
//        mChart.setMarkerView(mv);

    }

    @Override
    public void showBloodPressureList(ArrayList<BPListBean> bpListBeanList, CombinedData combinedData) {
        initAdapter();
        mBPRVAdapter.setDatas(bpListBeanList);

        mChart.setData(combinedData);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);
        // limit the number of visible entries
        mChart.setVisibleXRangeMaximum(itemMaxX);
        // move to the latest entry
        mChart.moveViewToX(combinedData.getXValCount() - itemMaxX - 1);
    }

    @Override
    public void appendBloodPressureList(ArrayList<BPListBean> bpListBeanList) {
        initAdapter();
        mBPRVAdapter.appendDatas(bpListBeanList);
    }

    private void initAdapter() {
        if (mBPRVAdapter == null) {
            mBPRVAdapter = new BPRVAdapter();
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBPRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(decoration);
            mBPRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    decoration.invalidateHeaders();
                }
            });
            mRecyclerView.setAdapter(mBPRVAdapter);
        }
    }

    private void addLimitLines(YAxis leftAxis) {
        LimitLine ll1 = new LimitLine(140f, "收缩压(高)");
        ll1.setLineWidth(2f);
        ll1.setLineColor(Color.parseColor("#69c88e"));
        ll1.setTextColor(Color.parseColor("#69c88e"));
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(91f, "收缩压(低)");
        ll2.setLineColor(Color.parseColor("#69c88e"));
        ll2.setTextColor(Color.parseColor("#69c88e"));
        ll2.setLineWidth(2f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        LimitLine ll3 = new LimitLine(90f, "舒张压(高)");
        ll3.setLineWidth(2f);
        ll3.setLineColor(Color.parseColor("#12b9f8"));
        ll3.setTextColor(Color.parseColor("#12b9f8"));
        ll3.enableDashedLine(10f, 10f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        ll3.setTextSize(10f);

        LimitLine ll4 = new LimitLine(60f, "舒张压(低)");
        ll4.setLineColor(Color.parseColor("#12b9f8"));
        ll4.setTextColor(Color.parseColor("#12b9f8"));
        ll4.setLineWidth(2f);
        ll4.enableDashedLine(10f, 10f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll4.setTextSize(10f);

        LimitLine ll5 = new LimitLine(70f, "心率(低)");
        ll5.setLineColor(Color.parseColor("#de0404"));
        ll5.setTextColor(Color.parseColor("#de0404"));
        ll5.setLineWidth(2f);
        ll5.enableDashedLine(10f, 10f, 0f);
        ll5.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll5.setTextSize(10f);

        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);
        leftAxis.addLimitLine(ll5);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBPRVAdapter = null;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bphr));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bphr));
    }
}
