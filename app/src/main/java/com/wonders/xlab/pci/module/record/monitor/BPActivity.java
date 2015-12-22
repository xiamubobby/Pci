package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.adapter.BpAdapter;
import com.wonders.xlab.pci.module.record.adapter.ReportDetailRVAdapter;
import com.wonders.xlab.pci.module.record.bean.BpBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.BPModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BPActivity extends AppbarActivity implements BPView {

    private final String[] TIME_FILTER_NAME = new String[]{"周", "月", "年"};


    @Bind(R.id.stl_bp_time_filter)
    SegmentTabLayout mStlBpTimeFilter;
    @Bind(R.id.ry_bp_history)
    RecyclerView mRyBpHistroy;
    private  BPModel bpModel;
    private BpAdapter mBpAdapter;

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

        bpModel = new BPModel(this);
        addModel(bpModel);
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
        mRyBpHistroy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bpModel.getBpData(AIManager.getInstance(this).getUserId());
      //  mRyBpHistroy.addItemDecoration(new StickyRecyclerHeadersDecoration(mBpAdapter));
    }


    @Override
    public void showBplist(List<BpBean> bpBeanList) {
        if (mBpAdapter == null) {
            mBpAdapter = new BpAdapter(new WeakReference<Context>(this));
            mRyBpHistroy.setAdapter(mBpAdapter);
        }
        mBpAdapter.setDatas(bpBeanList);

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
