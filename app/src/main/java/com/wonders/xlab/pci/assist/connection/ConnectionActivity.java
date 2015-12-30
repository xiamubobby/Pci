package com.wonders.xlab.pci.assist.connection;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;
import com.wonders.xlab.pci.module.base.AppbarActivity;


/**
 * @author hua
 *         扫描设备你；－借记卡6
 *         去、 *         血氧，心率
 */
public abstract class ConnectionActivity extends AppbarActivity implements BluetoothService.OnConnectListener, BluetoothService.OnReceiveDataListener {
    public static final String TAG = "ConnectThread";

    private BluetoothService.DEVICE_TYPE mDeviceType = BluetoothService.DEVICE_TYPE.NONE;

    private final int REQUEST_ENABLE_BT = 0;

    private boolean isRegister = false;

    private boolean mAutoRetry;

    public String mCurDeviceMacAddress;

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothService mBluetoothService;

    private OnScanListener mOnScanListener;

    public void setAutoRetry(boolean autoRetry) {
        mAutoRetry = autoRetry;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetoothService == null) {
            mBluetoothService = new BluetoothService();
        }

        mBluetoothService.setOnReceiveDataListener(this);
        mBluetoothService.setOnConnectListener(this);
    }

    /**
     * 开始搜索设备
     *
     * @param autoRetry 自动重新扫描
     */
    public void scan(boolean autoRetry) {
        mAutoRetry = autoRetry;
        startScan();
    }

    /**
     * 连接设备并进行数据请求
     *
     * @param deviceType    {@link BluetoothService.DEVICE_TYPE.BG} or {@link BluetoothService.DEVICE_TYPE.BP}
     * @param deviceAddress
     */
    public void requestData(BluetoothService.DEVICE_TYPE deviceType, String deviceAddress) {
        Log.d(TAG, "可以绑定，连接设备并进行数据请求");
        if (mDeviceType == deviceType) {
            return;
        } else {
            mDeviceType = deviceType;
        }

        mAutoRetry = false;
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        mBluetoothService.requestData(deviceType, deviceAddress);
    }

    private void startScan() {
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                Log.d(TAG, "请求设备开启蓝牙");
            } else {
                if (!isRegister) {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(BluetoothDevice.ACTION_FOUND);
                    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                    filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
                    filter.setPriority(Integer.MAX_VALUE);

                    registerReceiver(mReceiver, filter);
                    isRegister = true;
                    Log.d(TAG, "注册蓝牙监听广播");
                }
                if (mBluetoothAdapter.isDiscovering()) mBluetoothAdapter.cancelDiscovery();
                boolean isStartSuccess = mBluetoothAdapter.startDiscovery();
                if (!isStartSuccess) {
                    Log.d(TAG, "startDiscovery()失败");
                } else {
                    Log.d(TAG, "startDiscovery()成功");
                }
            }
        } else {
            Log.d(TAG, "设备不支持蓝牙！");

        }
    }

    @Override
    public void connectStarted() {
        Log.d(TAG, "正在连接设备...");
    }

    @Override
    public void onConnectSuccess(BluetoothService.DEVICE_TYPE deviceType, String macAddress) {
        scan(true);
        Log.d(TAG, "设备连接成功");
    }

    @Override
    public void onConnectFailed() {
        Log.d(TAG, "设备连接失败");
        scan(true);
        mDeviceType = BluetoothService.DEVICE_TYPE.NONE;
    }

    @Override
    public void onReceiveData(BaseConnectionEntity o) {

    }

    @Override
    public void onReceiveFailed() {
        mDeviceType = BluetoothService.DEVICE_TYPE.NONE;
        mAutoRetry = false;
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
//                    ToastManager.get().show(getActivity(), "已开启蓝牙");
                    startScan();
                } else if (resultCode == Activity.RESULT_CANCELED) {
//                    ToastManager.get().show(getActivity(), "取消授权！");
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancel();
    }

    public void cancel() {
        mAutoRetry = false;
        mCurDeviceMacAddress = "";

        if (isRegister) {
            unregisterReceiver(mReceiver);
            Log.d(TAG, "取消注册蓝牙监听广播");
            isRegister = false;
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (mBluetoothService != null) {
            mBluetoothService.stop();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d(TAG, "开始扫描附近蓝牙设备");
                //discovery starts, we can show progress dialog or perform other tasks
                if (mOnScanListener != null) {
                    mOnScanListener.onScanStart();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "搜索蓝牙结束");

                if (mOnScanListener != null) {
                    mOnScanListener.onScanFinished();
                }
                //不断扫描
                if (mAutoRetry) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startScan();
                        }
                    }, 5000);
                }

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (!TextUtils.isEmpty(device.getName())
                        && !TextUtils.isEmpty(device.getAddress())) {

                    if (device.getName().contains(BluetoothService.DEVICE_TYPE.BG.toString()) ||
                            device.getName().contains(BluetoothService.DEVICE_TYPE.BP.toString())) {
                        Log.i(TAG, "搜索到可用设备：" + device.getName());
                    } else {
                        Log.i(TAG, "搜索到无关设备：" + device.getName());
                    }

                    if (mOnScanListener != null) {
                        mOnScanListener.onFoundDevice(device);
                    }
                }
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                Log.d(TAG, "蓝牙连接状态改变");
                int preState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, -1);
                int currentState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1);
                if (preState != currentState && currentState == BluetoothAdapter.STATE_DISCONNECTED) {
                    mBluetoothService.stop();
                }
            }
        }
    };

    public void setOnScanListener(OnScanListener onScanListener) {
        mOnScanListener = onScanListener;
    }

    public BluetoothService.DEVICE_TYPE getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(BluetoothService.DEVICE_TYPE deviceType) {
        this.mDeviceType = deviceType;
    }

    /**
     * 搜索蓝牙设备接口
     */
    public interface OnScanListener {
        void onScanStart();

        void onScanFinished();

        void onFoundDevice(BluetoothDevice device);
    }

}
