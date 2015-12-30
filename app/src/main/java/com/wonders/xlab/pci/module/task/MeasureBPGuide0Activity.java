package com.wonders.xlab.pci.module.task;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

public class MeasureBPGuide0Activity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bp_guide_0;
    }

    @Override
    public String getToolbarTitle() {
        return "血压测量";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
