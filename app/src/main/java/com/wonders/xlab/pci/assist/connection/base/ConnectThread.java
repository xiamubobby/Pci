package com.wonders.xlab.pci.assist.connection.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by hua on 15/10/26.
 * 连接设备的线程
 */
public class ConnectThread extends Thread {
    private final UUID PUBLIC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final String TAG = "ConnectThread";

    private OnConnectListener mOnConnectListener;

    //设备MAC地址
    private String macAddress;
    private boolean connected = false;
    private final int RETRY_CONNECT_TIMES = Integer.MAX_VALUE;
    private int connectTime = 0;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mSocket;
    private BluetoothAdapter mBluetoothAdapter;

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        mOnConnectListener = onConnectListener;
    }

    public interface OnConnectListener {
        void connectStarted();

        /**
         * 成功时回调，必须把用于connect的socket回传给需要接收数据的Thread
         *
         * @param socket
         */
        void connectSuccess(BluetoothSocket socket, String macAddress);

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

        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        try {
            mSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(PUBLIC_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        while (!connected && connectTime <= RETRY_CONNECT_TIMES) {
            tryToConnect();
        }
    }

    private void tryToConnect() {
        if (mBluetoothDevice == null) return;
        if (mOnConnectListener != null) mOnConnectListener.connectStarted();

        try {
            // 连接之前先配对
            if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                Method creMethod = BluetoothDevice.class
                        .getMethod("createBond");
                Log.d(TAG, "开始配对");
                creMethod.invoke(mBluetoothDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "设备(" + mBluetoothDevice.getName() + "）无法进行配对");
        }

        try {
            mSocket.connect();
            if (mOnConnectListener != null) {
                mOnConnectListener.connectSuccess(mSocket, mBluetoothDevice.getAddress());
            }
            connected = true;
            connectTime = 0;
        } catch (IOException e) {
            Log.e(TAG, "无法连接设备:" + mBluetoothDevice.getName());

            if (connectTime >= RETRY_CONNECT_TIMES) {
                cancel();
                if (mOnConnectListener != null) {
                    mOnConnectListener.connectFailed();
                }
            }

            connectTime++;
            connected = false;
        }
    }

    public void cancel() {
        try {
            if (mSocket != null) {
                mSocket.close();
                Log.d("ConnectThread", "socket close");
            }
        } catch (IOException e) {
            Log.e(TAG, "unable to close() " +
                    " socket during connection failure", e);
        }
    }
}