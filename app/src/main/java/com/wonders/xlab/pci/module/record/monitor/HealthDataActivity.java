package com.wonders.xlab.pci.module.record.monitor;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthDataActivity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_health_data;
    }

    @Override
    public String getToolbarTitle() {
        return "监测数据";
    }

    public HealthDataActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_monitor_bp)
    public void onBPClick() {
        startActivity(new Intent(this, BPActivity.class));
    }

    @OnClick(R.id.tv_monitor_bs)
    public void onBSClick() {
        startActivity(new Intent(this, BSActivity.class));
    }

    @OnClick(R.id.tv_monitor_symptom)
    public void onSymptomClick() {
        startActivity(new Intent(this, SymptomActivity.class));
    }

    @OnClick(R.id.tv_monitor_medicine)
    public void onMedicineClick() {
        startActivity(new Intent(this, MedicineActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
