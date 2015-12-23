package com.wonders.xlab.pci.module;

import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.ButterKnife;

public class UserCenterActivity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public String getToolbarTitle() {
        return "个人中心";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}
