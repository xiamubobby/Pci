package com.wonders.xlab.patient.module.home.dailyrecord;

import android.content.Intent;
import android.os.Bundle;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.home.dailyrecord.bp.BPAddActivity;
import com.wonders.xlab.patient.module.home.dailyrecord.bp.BPGuideActivity;
import com.wonders.xlab.patient.module.home.dailyrecord.bs.BSAddActivity;
import com.wonders.xlab.patient.module.home.dailyrecord.bs.BSGuideActivity;
import com.wonders.xlab.patient.module.home.dailyrecord.symptom.SymptomActivityListener;

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
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            recordNewData(BPGuideActivity.class);
        } else {
            recordNewData(BPAddActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_bs)
    public void onRecordBsClick() {
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            recordNewData(BSGuideActivity.class);
        } else {
            recordNewData(BSAddActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_symptom)
    public void onRecordSymptomClick() {
        recordNewData(SymptomActivityListener.class);
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
