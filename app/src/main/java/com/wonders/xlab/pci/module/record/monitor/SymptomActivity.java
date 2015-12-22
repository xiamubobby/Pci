package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

public class SymptomActivity extends AppbarActivity {

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
