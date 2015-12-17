package com.wonders.xlab.pci.module.task;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.ButterKnife;

public class AddBloodPressureActivity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_blood_pressure;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

}
