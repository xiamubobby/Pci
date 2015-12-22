package com.wonders.xlab.pci.module;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.ButterKnife;

public class MyDoctorActivity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_doctor;
    }

    @Override
    public String getToolbarTitle() {
        return "我的医生";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}
