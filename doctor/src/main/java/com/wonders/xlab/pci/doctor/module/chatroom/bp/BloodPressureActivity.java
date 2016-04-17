package com.wonders.xlab.pci.doctor.module.chatroom.bp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.adapter.BPRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.bean.BPListBean;
import com.wonders.xlab.pci.doctor.data.presenter.impl.BPPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BloodPressureActivity extends AppbarActivity implements BPPresenter.BPPresenterListener {
    public static final String EXTRA_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.chart_blood_pressure)
    CombinedChart mChart;

    private BPPresenter mBPPresenter;
    private BPRVAdapter mBPRVAdapter;

    private final int itemMaxX = 30;

    @Nullable
    @Bind(R.id.recycler_view_blood_pressure)
    PullLoadMoreRecyclerView mRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.blood_pressure_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取患者血压信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取患者血压信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getToolbar().inflateMenu(R.menu.menu_blood_pressure);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bp_refresh:
                        if (mRecyclerView != null) {
                            mRecyclerView.setRefreshing(true);
                        }
                        mBPPresenter.getBPList(mPatientId,0, Calendar.getInstance().getTimeInMillis());
                        break;
                }
                return false;
            }
        });

        initChart();

        if (null != mRecyclerView) {
            mRecyclerView.setLinearLayout(false);
            mRecyclerView.setPullRefreshEnable(false);
            mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
            mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                @Override
                public void onRefresh() {
                    mBPPresenter.getBPList(mPatientId,0, Calendar.getInstance().getTimeInMillis());
                }

                @Override
                public void onLoadMore() {

                }
            });
        }

        mBPPresenter = new BPPresenter(this);
        addPresenter(mBPPresenter);

        if (null != mRecyclerView) {
            mRecyclerView.setRefreshing(true);
        }
        mBPPresenter.getBPList(mPatientId,0, Calendar.getInstance().getTimeInMillis());
    }

    private void initChart() {
        mChart.setPinchZoom(true);
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
        if (mRecyclerView != null) {
            mRecyclerView.setPullLoadMoreCompleted();
        }
        if (null != mRecyclerView) {
            if (mBPRVAdapter == null) {
                mBPRVAdapter = new BPRVAdapter();
                mRecyclerView.setAdapter(mBPRVAdapter);
                final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBPRVAdapter);
                mRecyclerView.getRecyclerView().addItemDecoration(decoration);
                mBPRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        decoration.invalidateHeaders();
                    }
                });
            }

            mBPRVAdapter.setDatas(bpListBeanList);
        }

        mChart.setData(combinedData);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);
        // limit the number of visible entries
        mChart.setVisibleXRangeMaximum(itemMaxX);
        // move to the latest entry
        mChart.moveViewToX(combinedData.getXValCount() - itemMaxX - 1);
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
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        if (mRecyclerView != null) {
            mRecyclerView.setPullLoadMoreCompleted();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

    }

}
