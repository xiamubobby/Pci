package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import com.wonders.xlab.pci.module.record.monitor.adapter.BSAdapter;
import com.wonders.xlab.pci.module.record.monitor.bean.BSBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.BSModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BSView;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSActivity extends AppbarActivity implements BSView {
    private final String[] TIME_FILTER_NAME = new String[]{"周", "月", "年"};

    @Bind(R.id.stl_bs_time_filter)
    SegmentTabLayout mStlBsTimeFilter;
    @Bind(R.id.tv_bs_date)
    TextView mTvBsDate;
    @Bind(R.id.rv_bs_history)
    RecyclerView mRvBsHistory;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private BSModel mBSModel;
    private BSAdapter mBSAdapter;

    private SelectedPeriod mSelectedPeriod = SelectedPeriod.WEEK;

    private enum SelectedPeriod {
        WEEK, MONTH, YEAR
    }

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    public int getContentLayout() {
        return R.layout.activity_bs;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBSModel = new BSModel(this);
        addModel(mBSModel);

        initView();
        mBSModel.getBSData(AIManager.getInstance(BSActivity.this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
    }

    private void initView() {
        mTvBsDate.setText(String.format("%s~%s", getStartTime(), getEndTime()));

        mStlBsTimeFilter.setTabData(TIME_FILTER_NAME);
        mStlBsTimeFilter.setOnTabSelectListener(new OnTabSelectListener() {
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
                mTvBsDate.setText(String.format("%s~%s", getStartTime(), getEndTime()));

                mBSModel.getBSData(AIManager.getInstance(BSActivity.this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mRvBsHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick(R.id.tv_bs_pre_date)
    public void onPreClick() {
        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            mCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            mCalendar.add(Calendar.MONTH, -1);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            mCalendar.add(Calendar.YEAR, -1);
        }
        mTvBsDate.setText(String.format("%s~%s", getStartTime(), getEndTime()));
        mBSModel.getBSData(AIManager.getInstance(this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));

    }

    @OnClick(R.id.tv_bs_after_date)
    public void onAfterClick() {
        if (mSelectedPeriod == SelectedPeriod.WEEK) {
            mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        } else if (mSelectedPeriod == SelectedPeriod.MONTH) {
            mCalendar.add(Calendar.MONTH, 1);
        } else if (mSelectedPeriod == SelectedPeriod.YEAR) {
            mCalendar.add(Calendar.YEAR, 1);
        }
        mTvBsDate.setText(String.format("%s~%s", getStartTime(), getEndTime()));
        mBSModel.getBSData(AIManager.getInstance(this).getUserId(), DateUtil.parseToLong(getStartTime(), "yyyy-MM-dd"), DateUtil.parseToLong(getEndTime(), "yyyy-MM-dd"));
    }

    @Override
    public void showBSlist(List<BSBean> bpBeanList) {
        if (mBSAdapter == null) {
            mBSAdapter = new BSAdapter(new WeakReference<Context>(this));
            mRvBsHistory.setAdapter(mBSAdapter);
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBSAdapter);
            mRvBsHistory.addItemDecoration(decoration);
            mBSAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    decoration.invalidateHeaders();
                }
            });
        }
        mBSAdapter.setDatas(bpBeanList);
    }

    @Override
    public void onFailed(String message) {
        showSnackbar(mCoordinator, message);
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
