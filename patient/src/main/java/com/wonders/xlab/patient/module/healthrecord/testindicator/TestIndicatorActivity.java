package com.wonders.xlab.patient.module.healthrecord.testindicator;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

/**
 * Created by jimmy on 16/5/10.
 */
public class TestIndicatorActivity extends AppbarActivity {
    @Override
    public int getContentLayout() {
        return R.layout.test_indicator_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.health_record_test_indicator);
    }
}
