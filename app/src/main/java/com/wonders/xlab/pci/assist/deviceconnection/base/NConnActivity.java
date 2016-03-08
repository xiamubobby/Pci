package com.wonders.xlab.pci.assist.deviceconnection.base;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.assist.deviceconnection.BPConnectedThread;
import com.wonders.xlab.pci.assist.deviceconnection.BSConnectedThread;
import com.wonders.xlab.pci.assist.deviceconnection.aamodel.BPAAModel;
import com.wonders.xlab.pci.assist.deviceconnection.aamodel.BSAAModel;
import com.wonders.xlab.pci.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import java.util.Set;

/**
 * Created by hua on 15/12/31.
 * <p/>
 * 使用步骤：
 * 1、@{@link #startScan()} 获取扫描的设备的信息 通过Otto注册接收扫描的结果，具体参考@{@link ScanReceiver}
 * 2、@{@link #getData(String)} 将第一步获取的设备地址传入，开始获取数据
 * 3、接收数据，也是通过Otto的注册方式来接收，目前只有@{@link BPAAModel}和@{@link BSAAModel}
 */
public abstract class NConnActivity extends AppbarActivity {
    public static final int REQUEST_ENABLE_BOND = 11214;

    private final int REQUEST_ENABLE_BT = 11213;
    /**
     * health monitoring device names
     */
    private static final String DEVICE_TYPE_BS = "BG";  // blood sugar CMSSXT
    private static final String DEVICE_TYPE_BP = "NIBP";   // blood pressure CONTEC08A
    private static final String DEVICE_TYPE_WT = "WT";  // weight WT100
    private static final String DEVICE_TYPE_BO = "SpO";  // blood oxygen CMS50EW
    private static final String TAG = "NConnActivity";


    private ScanReceiver mScanReceiver;
    private boolean isRegistered = false;

    public BluetoothAdapter mBluetoothAdapter;

    private String mDeviceAddress;
    private ConnectThread mConnectThread;//配对线程

    private DataRequestThread mRequestDataThread;//请求数据线程

//    private DEVICE_TYPE mDeviceType;

    private final int RETRY_CONNECT_TIMES = 0;
    private int mConnectTime = 0;

    /**
     * 是否在连接失败时发送Otto消息
     */
    private boolean mShowConnectionFailedInfo = true;

    private boolean mIsCancel = true;

    /**
     * 设备类型
     */
    public enum DEVICE_TYPE {
        NONE(""), BS(DEVICE_TYPE_BS), BP(DEVICE_TYPE_BP);

        DEVICE_TYPE(String name) {
            this.name = name;
        }

        private String name;

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }

    private final int MAX_RETRY_TIME = 8;
    private int mRetryTimes = 0;

    private Handler mScanHandler;
    private Runnable mScanRunnable;

    public abstract DEVICE_TYPE getDeviceType();

    /**
     * 首先尝试连接已经绑定的设备，如果连接失败了，会有连接失败的回调，然后再进行扫描
     */
    public void connectBondedDevice() {

        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        if (bondedDevices.size() == 0) {
            startScan();
            return;
        }
        for (BluetoothDevice device : bondedDevices) {
            if (device.getName().contains(getDeviceType().toString())) {
                getData(device.getAddress());
                return;
            }
        }
        startScan();
    }

    public void postDelayScan(long delay) {

        if (!mIsCancel) {
            if (mScanHandler == null) {
                mScanHandler = new Handler();
            }
            if (mScanRunnable == null) {
                mScanRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!mIsCancel) {
                            startScan();
                        }
                    }
                };
            }
            mScanHandler.postDelayed(mScanRunnable, delay);
        }
    }

    public void startScan() {
        mIsCancel = false;
        //reset flag
        showConnectionFailedInfo(true);

        if (mRetryTimes++ <= MAX_RETRY_TIME) {
            if (!mBluetoothAdapter.isEnabled()) {
                if (BuildConfig.DEBUG) Log.d(TAG, "请求设备开启蓝牙");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                registerScanReceiver();
                if (mBluetoothAdapter.isDiscovering()) {
                    if (BuildConfig.DEBUG) Log.d(TAG, "cancelDiscovery");
                    mBluetoothAdapter.cancelDiscovery();
                }
                boolean isStartSuccess = mBluetoothAdapter.startDiscovery();
                if (!isStartSuccess) {
                    Toast.makeText(this, "搜索设备失败，请确认蓝牙已打开并且授予蓝牙使用权限后重试！", Toast.LENGTH_LONG).show();
                } else {
                    if (BuildConfig.DEBUG) Log.d(TAG, "开始搜索设备");
                }
            }
        } else {
            mRetryTimes = 0;
            Toast.makeText(this, "长时间无法搜索到您的设备，可以先关闭蓝牙，然后再次打开蓝牙后重试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 连接设备，成功后请求数据
     */
    public void getData(String deviceAddress) {
        mIsCancel = false;

        if (BuildConfig.DEBUG) Log.d(TAG, "请求数据");
        mDeviceAddress = deviceAddress;
        connectAndStartRequestDataThread(true);
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
            case REQUEST_ENABLE_BOND:
                connectBondedDevice();
                break;
        }
    }

    /**
     * 建立连接
     * <p/>
     * 成功后开始数据请求
     *
     * @param autoRequestData
     */
    private void connectAndStartRequestDataThread(final boolean autoRequestData) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mRequestDataThread != null) {
            mRequestDataThread.cancel();
            mRequestDataThread = null;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mConnectThread = new ConnectThread(mDeviceAddress);
        mConnectThread.setOnConnectListener(new ConnectThread.OnConnectListener() {
            @Override
            public void connectStarted() {
                OttoManager.post(new ConnStatusOtto(ConnStatusOtto.STATUS.START));
            }

            @Override
            public void connectSuccess(BluetoothSocket socket) {
                //连接成功，停止扫描
                mIsCancel = true;

                OttoManager.post(new ConnStatusOtto(ConnStatusOtto.STATUS.SUCCESS));
                if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                if (isRegistered) {
                    if (BuildConfig.DEBUG) Log.d(TAG, "取消注册蓝牙监听广播");
                    unregisterReceiver(mScanReceiver);
                    isRegistered = false;
                }

                if (autoRequestData) {
                    //TODO 后面可新增自己的设备，在此处加入类型即可
                    switch (getDeviceType()) {
                        case BP:
                            requestData(new BPConnectedThread(socket));
                            break;
                        case BS:
                            requestData(new BSConnectedThread(socket));
                            break;
                    }
                }
            }

            @Override
            public void connectFailed() {
                if (mConnectTime++ < RETRY_CONNECT_TIMES) {
                    connectAndStartRequestDataThread(autoRequestData);
                } else {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "mShowConnectionFailedInfo:" + mShowConnectionFailedInfo);
                    if (mShowConnectionFailedInfo) {
                        OttoManager.post(new ConnStatusOtto(ConnStatusOtto.STATUS.FAILED));
                    }
                    mConnectTime = 0;
                }

            }
        });
        mConnectThread.start();
    }

    public void showConnectionFailedInfo(boolean show) {
        mShowConnectionFailedInfo = show;
    }

    public boolean isShowConnectionFailedInfo() {
        return mShowConnectionFailedInfo;
    }

    private void requestData(DataRequestThread thread) {
        mRequestDataThread = thread;
        mRequestDataThread.start();
    }

    private void registerScanReceiver() {
        if (mScanReceiver == null) {
            mScanReceiver = new ScanReceiver();
        }
        if (!isRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            filter.setPriority(Integer.MAX_VALUE);
            registerReceiver(mScanReceiver, filter);

            isRegistered = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }

    public void cancel() {
        mIsCancel = true;

        if (BuildConfig.DEBUG) Log.d(TAG, "cancel");
        if (mScanHandler != null) {
            mScanHandler.removeCallbacks(mScanRunnable);
            mScanHandler = null;
            mScanRunnable = null;
        }
        if (isRegistered && mScanReceiver != null) {
            unregisterReceiver(mScanReceiver);
            mScanReceiver = null;
            isRegistered = false;
        }
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (mRequestDataThread != null) {
            mRequestDataThread.cancel();
            mRequestDataThread = null;
        }
        if (mConnectThread != null) {
            mConnectThread.setOnConnectListener(null);
            mConnectThread.cancel();
            mConnectThread = null;
        }
    }
}
