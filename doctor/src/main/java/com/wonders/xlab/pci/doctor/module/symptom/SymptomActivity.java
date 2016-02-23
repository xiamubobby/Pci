package com.wonders.xlab.pci.doctor.module.symptom;

import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SymptomActivity extends AppbarActivity {

    @Bind(R.id.recycler_view_symptom)
    PullLoadMoreRecyclerView mRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.symptom_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "不适症状";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
