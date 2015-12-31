package com.wonders.xlab.pci.assist.connection.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.assist.connection.otto.FindDeviceOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanEndOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;

/**
 * 外部类需要获取搜索的状态和搜索到的设备的信息，注册Otto来接收即可
 */
public class ScanReceiver extends BroadcastReceiver {
    private static final String TAG = "ScanReceiver";

    public ScanReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            Log.d(TAG, "开始扫描附近蓝牙设备");
            OttoManager.post(new ScanStartOtto());
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Log.d(TAG, "搜索蓝牙结束");
            OttoManager.post(new ScanEndOtto());
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            //bluetooth device found
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (!TextUtils.isEmpty(device.getName())
                    && !TextUtils.isEmpty(device.getAddress())) {
                Log.d(TAG, device.getName());
                OttoManager.post(new FindDeviceOtto(device.getName(),device.getAddress()));
            }
        } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
            Log.d(TAG, "蓝牙连接状态改变");
            int preState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, -1);
            int currentState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1);
            if (preState != currentState && currentState == BluetoothAdapter.STATE_DISCONNECTED) {

            }
        }
    }
}
