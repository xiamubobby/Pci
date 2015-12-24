package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.adapter.BpAdapter;
import com.wonders.xlab.pci.module.record.monitor.bean.BpBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.BPModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BPActivity extends AppbarActivity implements BPView {

    private final String[] TIME_FILTER_NAME = new String[]{"周", "月", "年"};

    @Bind(R.id.stl_bp_time_filter)
    SegmentTabLayout mStlBpTimeFilter;
    @Bind(R.id.rv_bp_history)
    RecyclerView mRVBpHistory;
    @Bind(R.id.tv_bp_date)
    TextView tvBpDay;
    private BPModel bpModel;
    private BpAdapter mBpAdapter;

    private SelectedPeriod mSelectedPeriod = SelectedPeriod.WEEK;

    private enum SelectedPeriod {
        WEEK, MONTH, YEAR
    }

    private Calendar mCalendar = Calendar.getInstance();

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
        bpModel.getBpData(AIManager.getInstance(this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
    }

    private void initView() {
        tvBpDay.setText(String.format("%s~%s", getStartTime(), getEndTime()));

        mStlBpTimeFilter.setTabData(TIME_FILTER_NAME);
        mStlBpTimeFilter.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCalendar = Calendar.getInstance();
                switch (position) {
                    case 0:
                        mSelectedPeriod = SelectedPeriod.WEEK;
                        break;
                    case 1:
                        mSelectedPeriod = SelectedPeriod.MONTH;
                        break;
                    case 2:
                        mSelectedPeriod = SelectedPeriod.YEAR;
                        break;
                }
                tvBpDay.setText(String.format("%s~%s", getStartTime(), getEndTime()));

                bpModel.getBpData(AIManager.getInstance(BPActivity.this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mRVBpHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showBplist(List<BpBean> bpBeanList) {
        if (mBpAdapter == null) {
            mBpAdapter = new BpAdapter(new WeakReference<Context>(this));
            mRVBpHistory.setAdapter(mBpAdapter);
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBpAdapter);
            mRVBpHistory.addItemDecoration(decoration);
            mBpAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override public void onChanged() {
                    decoration.invalidateHeaders();
                }
            });
        }
        mBpAdapter.setDatas(bpBeanList);

    }

    @OnClick(R.id.tv_bp_pre_date)
    public void onPreClick() {
        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            mCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            mCalendar.add(Calendar.MONTH, -1);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            mCalendar.add(Calendar.YEAR, -1);
        }
        tvBpDay.setText(String.format("%s~%s", getStartTime(), getEndTime()));
        bpModel.getBpData(AIManager.getInstance(this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));

    }

    @OnClick(R.id.tv_bp_after_date)
    public void onAfterClick() {
        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            mCalendar.add(Calendar.MONTH, 1);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            mCalendar.add(Calendar.YEAR, 1);
        }
        tvBpDay.setText(String.format("%s~%s", getStartTime(), getEndTime()));
        bpModel.getBpData(AIManager.getInstance(this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
    }

    @Override
    public void onFailed(String message) {
        showSnackbar(mRVBpHistory, message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public String getStartTime() {
        long startTime = mCalendar.getTimeInMillis();

        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            startTime = DateUtil.calculateFirstDayOfWeek(startTime);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            startTime = DateUtil.calculateFirstDayOfMonth(startTime);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            startTime = DateUtil.calculateFirstDayOfYear(startTime);
        }

        return DateUtil.format(startTime, "yyyy-MM-dd");
    }

    public String getEndTime() {
        long endTime = mCalendar.getTimeInMillis();

        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            endTime = DateUtil.calculateLastDayOfWeek(endTime);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            endTime = DateUtil.calculateLastDayOfMonth(endTime);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            endTime = DateUtil.calculateLastDayOfYear(endTime);
        }
        return DateUtil.format(endTime, "yyyy-MM-dd");
    }


}
