package com.wonders.xlab.patient.module.healthrecord.healthrecords;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

/**
 * Created by jimmy on 16/5/10.
 */
public class HealthRecordsActivity extends AppbarActivity {
    @Override
    public int getContentLayout() {
        return R.layout.health_records_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_activity_medical_record);
    }
}
