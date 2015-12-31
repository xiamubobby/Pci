package com.wonders.xlab.pci.assist.connection;

import android.bluetooth.BluetoothSocket;
import android.support.annotation.NonNull;

import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;

import java.util.UUID;

/**
 * Created by hua on 15/10/27.
 */
public class BluetoothService implements BaseConnectedThread.OnReceiveDataListener {
    /*
     health monitoring device names
     */
    private static final String DEVICE_TYPE_BS = "BG";  // 血糖仪 CMSSXT
    private static final String DEVICE_TYPE_BP = "NIBP";   // 电子血压计 CONTEC08A
    private static final String DEVICE_TYPE_WT = "WT";  // 电子秤 WT100
    private static final String DEVICE_TYPE_BO = "SpO";  // 脉搏血氧仪 CMS50EW

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

    private DEVICE_TYPE mDeviceType;

    public static final UUID PUBLIC_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final int MAX_RETRY_TIMES = 20;//最大重试次数

    private int retryTimes;

    private boolean continueRetry = true;

    private String mAddress;

    private int mState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 2;  // now connected to a remote device
    public static final int STATE_CONNECT_FAILED = 3;  // failed to requestData to a remote device

    private ConnectThread mConnectThread;//配对线程

    private BaseConnectedThread mRequestDataThread;//请求数据线程

    private OnConnectListener mOnConnectListener;

    private OnReceiveDataListener mOnReceiveDataListener;

    public BluetoothService() {

    }

    /**
     * 此方法会先进行设备连接，然后开始请求数据
     *
     * @param deviceType * @see
     *                   {@link #DEVICE_TYPE_BS}
     *                   {@link #DEVICE_TYPE_BP}
     * @param address    设备地址
     */
    public synchronized void requestData(DEVICE_TYPE deviceType, String address) {
        this.mDeviceType = deviceType;
        this.mAddress = address;
        //init retry params
        continueRetry = true;
        retryTimes = 0;
        connectAndRequestData(true);
    }

    /**
     * 只进行设备配对
     *
     * @param address
     */
    public synchronized void connect(String address) {
        mAddress = address;
        connectAndRequestData(false);
    }

    /**
     * 断线重连,请求数据
     */
    private synchronized void continueRequestData() {
        connectAndRequestData(true);
    }

    /**
     * 连接设备，连接成功后开始请求数据
     *
     * @param shouldRequestData
     */
    private synchronized void connectAndRequestData(final boolean shouldRequestData) {
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mRequestDataThread != null) {
            mRequestDataThread.cancel();
            mRequestDataThread = null;
        }
        mConnectThread = new ConnectThread(mAddress);
        mConnectThread.setOnConnectListener(new ConnectThread.OnConnectListener() {
            @Override
            public void connectStarted() {
                if (mOnConnectListener != null) {
                    mOnConnectListener.connectStarted();
                }
            }

            @Override
            public void connectSuccess(BluetoothSocket socket, String macAddress) {

                if (mOnConnectListener != null) {
                    mOnConnectListener.onConnectSuccess(mDeviceType, macAddress);
                }
                if (shouldRequestData) {

                    switch (mDeviceType) {
                        case BP:
                            receiveData(new BPConnectedThread(socket));
                            break;
                        case BS:
                            receiveData(new BSConnectedThread(socket));
                            break;
                    }
                }
            }

            @Override
            public void connectFailed() {
                if (mOnConnectListener != null) {
                    mOnConnectListener.onConnectFailed();
                }
                retry();
            }
        });
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    private synchronized void receiveData(@NonNull BaseConnectedThread thread) {
        thread.setOnReceiveDataListener(this);
        mRequestDataThread = thread;
        mRequestDataThread.start();
    }

    @Override
    public void onReceiveData(BaseConnectionEntity o) {
        if (mOnReceiveDataListener != null) {
            mOnReceiveDataListener.onReceiveData(o);
        }
    }

    @Override
    public void onReceiveDataFailed() {
        if (mOnReceiveDataListener != null) {
            mOnReceiveDataListener.onReceiveFailed();
        }
        retry();
    }

    @Override
    public void retryRequest() {
        retry();
    }

    /**
     * 重试
     */
    private void retry() {
        retryTimes++;
        if (retryTimes <= MAX_RETRY_TIMES && continueRetry) {

            continueRequestData();
        } else {
            retryTimes = 0;
        }
    }

    public synchronized void stop() {
        cancel();
    }

    private void cancel() {
        continueRetry = false;
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mRequestDataThread != null) {
            mRequestDataThread.cancel();
            mRequestDataThread = null;
        }
    }

    /**
     * 设备配对回调
     */
    public interface OnConnectListener {
        void connectStarted();

        /**
         * @param deviceType * @see
         *                   {@link #DEVICE_TYPE_BS}
         *                   {@link #DEVICE_TYPE_BP}
         */
        void onConnectSuccess(DEVICE_TYPE deviceType, String macAddress);

        void onConnectFailed();
    }

    /**
     * 请求数据回调
     */
    public interface OnReceiveDataListener {
        void onReceiveData(BaseConnectionEntity o);

        void onReceiveFailed();
    }

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        mOnConnectListener = onConnectListener;
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        mOnReceiveDataListener = onReceiveDataListener;
    }

    public int getState() {
        return mState;
    }

    private void setState(int state) {
        mState = state;
    }

}
