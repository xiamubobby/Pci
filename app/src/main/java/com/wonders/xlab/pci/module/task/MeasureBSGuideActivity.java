package com.wonders.xlab.pci.module.task;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.BluetoothService;
import com.wonders.xlab.pci.assist.connection.ConnectionActivity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeasureBSGuideActivity extends ConnectionActivity implements ConnectionActivity.OnScanListener {

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
        setOnScanListener(this);
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
        scan(true);
    }

    @Override
    public void onScanStart() {
        showSnackbar(mCoordinator, "开始搜索设备...");
    }

    @Override
    public void onScanFinished() {

    }

    @Override
    public void onConnectSuccess(BluetoothService.DEVICE_TYPE deviceType, String macAddress) {
        super.onConnectSuccess(deviceType, macAddress);
        showSnackbar(mCoordinator, "已连接血糖设备！");
    }

    @Override
    public void onFoundDevice(BluetoothDevice device) {
        //如果搜寻到的设备是当前正在连接的设备，则忽略此次连接
        if (device.getAddress() != null && device.getAddress().equals(getCurrentDeviceAddress())) {
            return;
        }

        if (device.getName().contains(BluetoothService.DEVICE_TYPE.BS.toString())) {
            cancel();
            requestData(BluetoothService.DEVICE_TYPE.BS, device.getAddress());
            showSnackbar(mCoordinator, "找到血糖设备");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
