package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.adapter.SymptomRVAdapter;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.SymptomEntity;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.SymptomModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.SymptomView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SymptomActivity extends AppbarActivity implements SymptomView {

    @Bind(R.id.tv_symptom_date)
    TextView mTvSymptomDate;
    @Bind(R.id.fl_symptom_label)
    TagFlowLayout mFlSymptomLabel;
    @Bind(R.id.rv_symptom_advice)
    RecyclerView mRvSymptomAdvice;
    @Bind(R.id.refresh_symptom)
    SwipeRefreshLayout mRefreshSymptom;
    @Bind(R.id.tv_symptom_after_date)
    TextView mTvSymptomAfterDate;

    private SymptomRVAdapter mSymptomRVAdapter;

    private LayoutInflater mInflater;

    private SymptomModel mSymptomModel;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    public int getContentLayout() {
        return R.layout.activity_symptom;
    }

    @Override
    public String getToolbarTitle() {
        return "主诉症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);

        mRvSymptomAdvice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRefreshSymptom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCalendar = Calendar.getInstance();
                mSymptomModel.getSymptoms(AIManager.getInstance(SymptomActivity.this).getUserId(), mCalendar.getTimeInMillis());
            }
        });
        mSymptomModel = new SymptomModel(this);
        addModel(mSymptomModel);

        mSymptomModel.getSymptoms(AIManager.getInstance(this).getUserId(), mCalendar.getTimeInMillis());
    }

    @OnClick(R.id.tv_symptom_pre_date)
    public void onPreClick() {
        mCalendar.add(Calendar.DATE, -1);
        mSymptomModel.getSymptoms(AIManager.getInstance(this).getUserId(), mCalendar.getTimeInMillis());
    }

    @OnClick(R.id.tv_symptom_after_date)
    public void onAfterClick() {
        mCalendar.add(Calendar.DATE, 1);
        mSymptomModel.getSymptoms(AIManager.getInstance(this).getUserId(), mCalendar.getTimeInMillis());
    }

    private String formatDateStr(long time) {
        String formatStr = "MM月dd日";
        if (!DateUtil.isTheSameYear(time, Calendar.getInstance().getTimeInMillis())) {
            formatStr = "yyyy年MM月dd日";
        }

        return DateUtil.format(mCalendar.getTimeInMillis(), formatStr);
    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void showSymptomLabels(List<String> symptomList) {
        if (mFlSymptomLabel != null) {
            mFlSymptomLabel.removeAllViews();
        } else {
            return;
        }

        if (symptomList != null) {
            mFlSymptomLabel.setClickable(false);
            mFlSymptomLabel.setAdapter(new TagAdapter<String>(symptomList) {
                @Override
                public View getView(FlowLayout parent, int position, String label) {
                    TextView tvLabel = (TextView) mInflater.inflate(R.layout.item_symptom_content_label, parent, false);
                    tvLabel.setText(label);
                    return tvLabel;
                }
            });

        }
    }

    @Override
    public void showSymptomAdvices(List<SymptomEntity.RetValuesEntity.UserSymptomAdvicesEntity> symptomAdviceList) {
        if (symptomAdviceList == null) {
            return;
        }
        if (mSymptomRVAdapter == null) {
            mSymptomRVAdapter = new SymptomRVAdapter(new WeakReference<Context>(this));
            mRvSymptomAdvice.setAdapter(mSymptomRVAdapter);
        }
        mSymptomRVAdapter.setDatas(symptomAdviceList);
    }

    @Override
    public void showLoading() {
        mTvSymptomDate.setText(formatDateStr(mCalendar.getTimeInMillis()));

        mRefreshSymptom.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshSymptom != null) {
                    mRefreshSymptom.setRefreshing(true);
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        mRefreshSymptom.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshSymptom != null) {
                    mRefreshSymptom.setRefreshing(false);
                }
            }
        });
    }
}
