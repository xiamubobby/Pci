package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.Bind;

public class SymptomActivity extends AppbarActivity {

    @Bind(R.id.tv_symptom_date)
    TextView mTvSymptomDate;
    @Bind(R.id.fl_symptom_label)
    FlowLayout mFlSymptomLabel;
    @Bind(R.id.rv_symptom_advice)
    RecyclerView mRvSymptomAdvice;

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

    }

}
