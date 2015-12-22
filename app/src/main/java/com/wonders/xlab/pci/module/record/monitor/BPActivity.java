package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BPActivity extends AppbarActivity implements BPView {

    private final String[] TIME_FILTER_NAME = new String[]{"周", "月", "年"};

    @Bind(R.id.pie_bp_current)
    PieChart mPieBpCurrent;
    @Bind(R.id.stl_bp_time_filter)
    SegmentTabLayout mStlBpTimeFilter;
    @Bind(R.id.line_bp_history)
    LineChart mLineBpHistory;

    @Override
    public int getContentLayout() {
        return R.layout.activity_bp;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mStlBpTimeFilter.setTabData(TIME_FILTER_NAME);
        mStlBpTimeFilter.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Toast.makeText(BPActivity.this, TIME_FILTER_NAME[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void showCurrentBPPieChart() {

    }

    @Override
    public void showHistoryBPLineChart() {

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
