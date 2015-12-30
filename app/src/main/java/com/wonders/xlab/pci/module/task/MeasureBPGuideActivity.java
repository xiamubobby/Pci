package com.wonders.xlab.pci.module.task;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.BluetoothService;
import com.wonders.xlab.pci.assist.connection.ConnectionActivity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;

import butterknife.Bind;

public class MeasureBPGuideActivity extends ConnectionActivity implements ConnectionActivity.OnScanListener {

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
        setOnScanListener(this);
        scan(true);
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

    @Override
    public void onScanStart() {
        showSnackbar(mCoordinator, "开始搜索设备...");
    }

    @Override
    public void onScanFinished() {

    }

    @Override
    public void onFoundDevice(BluetoothDevice device) {
        //如果搜寻到的设备是当前正在连接的设备，则忽略此次连接
        if (device.getAddress() != null && device.getAddress().equals(mCurDeviceMacAddress)) {
            return;
        }

        if (device.getName().contains(BluetoothService.DEVICE_TYPE.BG.toString())) {
            cancel();
            requestData(BluetoothService.DEVICE_TYPE.BG, device.getAddress());
            showSnackbar(mCoordinator,"找到");
        } else if (device.getName().contains(BluetoothService.DEVICE_TYPE.BP.toString())) {
            cancel();
            requestData(BluetoothService.DEVICE_TYPE.BP, device.getAddress());
            showSnackbar(mCoordinator,"找到");
        }
    }

    @Override
    public void onReceiveData(BaseConnectionEntity o) {
        super.onReceiveData(o);
        if (o instanceof BSEntity) {
            BSEntity entity = (BSEntity) o;
            Log.d(TAG, "entity.getBloodSugar():" + entity.getBloodSugar());
        }
    }
}
