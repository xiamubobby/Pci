package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.base.NConnActivity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.otto.FindDeviceOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanEndOtto;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeasureBPGuideActivity extends NConnActivity {

    @Bind(R.id.ll_measure_bg_guide_0)
    LinearLayout mLlGuide0;
    @Bind(R.id.ll_measure_bg_guide_1)
    LinearLayout mLlGuide1;
    @Bind(R.id.btn_measure_bp_guide_0_start)
    Button mBtnGuide0Start;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bp_guide;
    }

    @Override
    public String getToolbarTitle() {
        return "血压测量";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        OttoManager.register(this);
    }

    private void showGuide0() {
        mLlGuide1.setVisibility(View.GONE);
        mLlGuide0.setVisibility(View.VISIBLE);
        mBtnGuide0Start.setVisibility(View.VISIBLE);
    }

    private void showGuide1() {
        mLlGuide0.setVisibility(View.GONE);
        mBtnGuide0Start.setVisibility(View.GONE);
        mLlGuide1.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_measure_bp_guide_0_start)
    public void start() {
        scan();
    }

    /**
     * 搜索到设备
     * @param otto
     */
    @Subscribe
    public void onFindDevice(FindDeviceOtto otto) {
        if (TextUtils.isEmpty(otto.getDeviceName())) {
            return;
        }

        if (otto.getDeviceName().contains(DEVICE_TYPE.BP.toString())) {
            Log.d("MeasureBPGuideActivity", otto.getDeviceName());
            getData(DEVICE_TYPE.BP,otto.getDeviceAddress());
        }
    }

    /**
     * 搜索结束
     * @param otto
     */
    @Subscribe
    public void onScanEnd(ScanEndOtto otto) {
        scan();
    }

    public void onDataReceived(BSEntity bsEntity) {
        Log.d("MeasureBPGuideActivity", "bsEntity.getBloodSugar():" + bsEntity.getBloodSugar());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
