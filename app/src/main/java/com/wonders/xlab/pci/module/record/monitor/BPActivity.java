package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.adapter.BpAdapter;
import com.wonders.xlab.pci.module.record.monitor.bean.BpBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.BPModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.BPView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BPActivity extends AppbarActivity implements BPView {

    private final String[] TIME_FILTER_NAME = new String[]{"周", "月", "年"};


    @Bind(R.id.stl_bp_time_filter)
    SegmentTabLayout mStlBpTimeFilter;
    @Bind(R.id.ry_bp_history)
    RecyclerView mRyBpHistroy;
    @Bind(R.id.tv_bp_day)
    TextView tvBpDay;
    private BPModel bpModel;
    private BpAdapter mBpAdapter;
    private long startTime;//开始时间
    private long endTime;//结束时间
    private int num = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        // Calendar calendar = Calendar.getInstance();

        //2015-12-01
        // calendar.add(Calendar.DATE,-1);
        // calendar.get
    }

    private void initView() {
        startTime = getStartTime(0, 0);
        endTime = getEndTime(0, 0);
        tvBpDay.setText(sdf.format(new Date(startTime))+"~"+sdf.format(new Date(endTime)));
        mStlBpTimeFilter.setTabData(TIME_FILTER_NAME);
        mStlBpTimeFilter.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                num = 0;
                startTime = getStartTime(position, 0);
                endTime = getEndTime(position, 0);
                tvBpDay.setText(sdf.format(new Date(startTime))+"~"+sdf.format(new Date(endTime)));
                bpModel.getBpData(AIManager.getInstance(BPActivity.this).getUserId(), startTime, endTime);
                // TextView textView= (TextView)findViewById(R.id.tv_bp_day);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mRyBpHistroy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bpModel.getBpData(AIManager.getInstance(this).getUserId(), startTime, endTime);
    }

    @Override
    public void showBplist(List<BpBean> bpBeanList) {
        if (mBpAdapter == null) {
            mBpAdapter = new BpAdapter(new WeakReference<Context>(this));
            mRyBpHistroy.addItemDecoration(new StickyRecyclerHeadersDecoration(mBpAdapter));
            mRyBpHistroy.setAdapter(mBpAdapter);
        }
        mBpAdapter.setDatas(bpBeanList);

    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                num--;
                break;
            case R.id.btn_right:
                num++;
                break;
            default:
                break;
        }
        startTime = getStartTime(mStlBpTimeFilter.getCurrentTab(), num);
        endTime = getEndTime(mStlBpTimeFilter.getCurrentTab(), num);
        tvBpDay.setText(sdf.format(new Date(startTime))+"~"+sdf.format(new Date(endTime)));
        bpModel.getBpData(AIManager.getInstance(this).getUserId(), startTime, endTime);

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

    public Long getStartTime(int position, int num) {
        Calendar calendar = Calendar.getInstance();
        switch (position) {
            case 0:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, -1);
                    calendar.set(Calendar.DAY_OF_WEEK, 1);
                    calendar.add(Calendar.DATE, num * 7);
                    startTime = calendar.getTimeInMillis();
                } else {
                    calendar.set(Calendar.DAY_OF_WEEK, 2);
                    calendar.add(Calendar.DATE, num * 7);
                    startTime = calendar.getTimeInMillis();
                }
                break;
            case 1:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, num * 1);
                startTime = calendar.getTimeInMillis();

                break;
            case 2:
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                calendar.add(Calendar.YEAR, num * 1);
                startTime = calendar.getTimeInMillis();
                break;
            default:
                break;
        }


        return startTime;
    }

    public Long getEndTime(int position, int num) {
        Calendar calendar = Calendar.getInstance();
        switch (position) {
            case 0:
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calendar.add(Calendar.DATE, num * 7);
                    endTime = calendar.getTimeInMillis();
                } else {
                    calendar.set(Calendar.DAY_OF_WEEK, 1);
                    calendar.add(Calendar.DATE, 7);
                    calendar.add(Calendar.DATE, num * 7);
                    endTime = calendar.getTimeInMillis();
                }
                break;
            case 1:
                calendar.add(Calendar.MONTH, 1);//本月最后一天
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                calendar.add(Calendar.MONTH, num * 1);
                endTime = calendar.getTimeInMillis();
                break;
            case 2:
                calendar.add(Calendar.YEAR, 1);
                calendar.set(Calendar.DAY_OF_YEAR, 1);//本年最后一天
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                calendar.add(Calendar.YEAR, num * 1);
                endTime = calendar.getTimeInMillis();
                break;
            default:
                break;
        }
        return endTime;
    }



}
