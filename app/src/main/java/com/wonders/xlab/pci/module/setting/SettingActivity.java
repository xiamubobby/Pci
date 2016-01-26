package com.wonders.xlab.pci.module.setting;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.Bind;

/**
 * Created by hua on 16/1/26.
 */
public class SettingActivity extends AppbarActivity {
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    @Override
    public int getContentLayout() {
        return R.layout.setting_layout;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.label_setting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .add(R.id.setting_container, SettingFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
