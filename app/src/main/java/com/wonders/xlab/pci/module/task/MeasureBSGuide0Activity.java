package com.wonders.xlab.pci.module.task;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

public class MeasureBSGuide0Activity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bsguide0;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖测量";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
