package com.wonders.xlab.patient.module.dailyrecord;

import android.content.Intent;
import android.os.Bundle;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.dailyrecord.bp.AddBPActivity;
import com.wonders.xlab.patient.module.dailyrecord.bp.MeasureBPGuideActivity;
import com.wonders.xlab.patient.module.dailyrecord.bs.AddBSActivity;
import com.wonders.xlab.patient.module.dailyrecord.bs.MeasureBSGuideActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyRecordActivity extends AppbarActivity {

    @Bind(R.id.fam_daily_task)
    FloatingActionsMenu mFamDailyTask;

    @Override
    public int getContentLayout() {
        return R.layout.daily_record_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "每日记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fam_daily_task_bp)
    public void onRecordBpClick() {
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), true);
        if (useEquipment) {
            recordNewData(MeasureBPGuideActivity.class);
        } else {
            recordNewData(AddBPActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_bs)
    public void onRecordBsClick() {
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), true);
        if (useEquipment) {
            recordNewData(MeasureBSGuideActivity.class);
        } else {
            recordNewData(AddBSActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_symptom)
    public void onRecordSymptomClick() {
        recordNewData(AddRetrieveSymptomActivity.class);
    }

    private void recordNewData(Class targetActivity) {
        startActivity(new Intent(this, targetActivity));
        mFamDailyTask.collapse();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
