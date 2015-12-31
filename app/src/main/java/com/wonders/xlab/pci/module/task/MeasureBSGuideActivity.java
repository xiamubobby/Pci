package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;

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

public class MeasureBSGuideActivity extends NConnActivity {

    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bs_guide;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖测量";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        OttoManager.register(this);
    }

    @OnClick(R.id.btn_measure_bs_guide_0_start)
    public void onStartClick() {
       /* Set<BluetoothDevice> bondedDevices = getBluetoothAdapter().getBondedDevices();
        while (bondedDevices.iterator().hasNext()) {
            BluetoothDevice device = bondedDevices.iterator().next();
            if (device.getName().contains(BluetoothService.DEVICE_TYPE.BS.toString())) {
                requestData(BluetoothService.DEVICE_TYPE.BS, device.getAddress());
                return;
            }
        }*/
        scan();
    }

    /**
     * 搜索到设备
     *
     * @param otto
     */
    @Subscribe
    public void onFindDevice(FindDeviceOtto otto) {
        if (TextUtils.isEmpty(otto.getDeviceName())) {
            return;
        }

        if (otto.getDeviceName().contains(DEVICE_TYPE.BS.toString())) {
            Log.d("MeasureBSGuideActivity", otto.getDeviceName());
            getData(DEVICE_TYPE.BS, otto.getDeviceAddress());
        }
    }

    /**
     * 搜索结束
     *
     * @param otto
     */
    @Subscribe
    public void onScanEnd(ScanEndOtto otto) {
        scan();
    }

    public void onDataReceived(BSEntity bsEntity) {
        Log.d("MeasureBSGuideActivity", "bsEntity.getBloodSugar():" + bsEntity.getBloodSugar());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
