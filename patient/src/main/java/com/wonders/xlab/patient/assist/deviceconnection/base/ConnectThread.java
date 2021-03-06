package com.wonders.xlab.patient.assist.deviceconnection.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Looper;
import android.util.Log;

import com.wonders.xlab.patient.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by hua on 15/10/26.
 * 连接设备的线程
 */
public class ConnectThread extends Thread {
    public static final UUID PUBLIC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final String TAG = "ConnectThread";

    private OnConnectListener mOnConnectListener;

    //设备MAC地址
    private String macAddress;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mSocket;

    private boolean mLoopFlag = true;

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        mOnConnectListener = onConnectListener;
    }

    public interface OnConnectListener {
        void connectStarted();

        /**
         * 成功时回调，必须把用于connect的socket回传给需要接收数据的Thread
         */
        void connectSuccess(BluetoothSocket socket);

        void connectFailed();
    }

    public ConnectThread(String address) {

        macAddress = address;
    }

    @Override
    public void run() {
        Looper.prepare();
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        try {
            mSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(PUBLIC_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        while (mLoopFlag) {
            tryToConnect();
        }
    }

    private void tryToConnect() {
        if (mBluetoothDevice == null) {
            return;
        }
        if (mOnConnectListener != null) mOnConnectListener.connectStarted();

        try {
            // 连接之前先配对
            if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                Method creMethod = BluetoothDevice.class
                        .getMethod("createBond");
                if (BuildConfig.DEBUG) Log.d(TAG, "开始配对");
                creMethod.invoke(mBluetoothDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "设备(" + mBluetoothDevice.getName() + "）无法进行配对");
            cancel();
        }

        try {
            if (mSocket != null) {
                mSocket.connect();
                if (mOnConnectListener != null) {
                    mOnConnectListener.connectSuccess(mSocket);
                }
                mLoopFlag = false;
            }
        } catch (IOException e) {
            Log.e(TAG, "无法连接设备:" + mBluetoothDevice.getName());
            cancel();
            if (mOnConnectListener != null) {
                mOnConnectListener.connectFailed();
            }
        }
    }

    public void cancel() {
        mLoopFlag = false;
        try {
            if (mSocket != null) {
                mSocket.close();
                if (BuildConfig.DEBUG) Log.d("ConnectThread", "socket close");
            }
        } catch (IOException e) {
            Log.e(TAG, "unable to close() " +
                    " socket during connection failure", e);
        }
    }
}