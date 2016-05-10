package com.wonders.xlab.patient.module.healthrecord.surgicalhistory;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

/**
 * Created by jimmy on 16/5/10.
 */
public class SurgicalHistoryActivity extends AppbarActivity {
    @Override
    public int getContentLayout() {
        return R.layout.surgical_history_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.health_record_surgical_history);
    }
}
