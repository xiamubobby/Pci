package com.wonders.xlab.pci.assist.connection.base;


import android.bluetooth.BluetoothAdapter;

import java.util.Calendar;

/**
 * Created by hua on 15/10/30.
 */
public abstract class DataRequestThread extends Thread {
    public BluetoothAdapter mBluetoothAdapter;

    public static final String TAG = "RequestDataThread";

    private final long RETRY_TIME_INTERVAL = 1000;//ms

    private long lastRetryTime;//ms

    public DataRequestThread() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

    }

    /**
     * 连接失败
     */
    protected void requestDataFailed() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastRetryTime < RETRY_TIME_INTERVAL) {
            return;
        }
        lastRetryTime = currentTime;
        cancel();
//        OttoManager.post(new ConnStatusOtto(ConnStatusOtto.STATUS.FAILED));
    }

    public abstract void cancel();

}
