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
import android.widget.Toast;

import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;
import com.wonders.xlab.pci.module.base.AppbarActivity;


/**
 * @author hua
 */
public abstract class ConnectionActivity extends AppbarActivity implements BluetoothService.OnConnectListener, BluetoothService.OnReceiveDataListener {
    public static final String TAG = "ConnectThread";

    private final int REQUEST_ENABLE_BT = 0;

    private boolean isRegister = false;

    private final int MAX_RETRY_TIMES = 15;

    private int mRetryTimes = 0;

    private boolean mAutoRetry;

    private String mCurrentDeviceAddress;

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothService mBluetoothService;

    private OnScanListener mOnScanListener;

    private Handler mRetryHandler;

    private Runnable mRetryRunnable;

    public void setAutoRetry(boolean autoRetry) {
        mAutoRetry = autoRetry;
    }

    public String getCurrentDeviceAddress() {
        return mCurrentDeviceAddress;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public void setOnScanListener(OnScanListener onScanListener) {
        mOnScanListener = onScanListener;
    }

    /**
     * 搜索蓝牙设备接口
     */
    public interface OnScanListener {
        void onScanStart();

        void onScanFinished();

        void onFoundDevice(BluetoothDevice device);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRetryHandler = new Handler();

        mRetryRunnable = new Runnable() {
            @Override
            public void run() {
                startScan();
            }
        };

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
                    Toast.makeText(this, "搜索设备失败，请重试！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "startDiscovery()成功");
                }
            }
        } else {
            Toast.makeText(this, "设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void connectStarted() {
        Log.d(TAG, "正在连接设备...");
    }

    @Override
    public void onConnectSuccess(BluetoothService.DEVICE_TYPE deviceType, String macAddress) {
        stopScan();
    }

    @Override
    public void onConnectFailed() {
        Log.d(TAG, "设备连接失败,重新搜索设备!");
        startScan();
    }

    /**
     * TODO 如果需要接收数据，在子类中重写该方法即可
     * @param o
     */
    @Override
    public void onReceiveData(BaseConnectionEntity o) {

    }
    /**
     * TODO 如果需要接收数据接收错误消息，在子类中重写该方法即可
     */
    @Override
    public void onReceiveFailed() {
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
                    Toast.makeText(this, "已开启蓝牙", Toast.LENGTH_SHORT).show();
                    startScan();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "取消授权!", Toast.LENGTH_SHORT).show();
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
        mRetryTimes = 0;
        mCurrentDeviceAddress = "";
        stopScan();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (mBluetoothService != null) {
            mBluetoothService.stop();
        }
    }

    private void stopScan() {
        if (mRetryHandler != null && mRetryRunnable != null) {
            mRetryHandler.removeCallbacks(mRetryRunnable);
        }
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (isRegister) {
            unregisterReceiver(mReceiver);
            Log.d(TAG, "取消注册蓝牙监听广播");
            isRegister = false;
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
                if (mAutoRetry && mRetryTimes <= MAX_RETRY_TIMES) {
                    mRetryHandler.postDelayed(mRetryRunnable, 5000);
                    mRetryTimes++;
                } else {
                    mRetryTimes = 0;
                }

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (!TextUtils.isEmpty(device.getName())
                        && !TextUtils.isEmpty(device.getAddress())) {

                    if (device.getName().contains(BluetoothService.DEVICE_TYPE.BS.toString()) ||
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
}
