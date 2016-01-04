package com.wonders.xlab.pci.assist.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.assist.connection.base.ConnectThread;
import com.wonders.xlab.pci.assist.connection.otto.FindDeviceOtto;

import java.io.IOException;

/**
 * Created by hua on 16/1/4.
 */
public class ServerThread extends Thread {
    private final BluetoothAdapter adapter;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;

    private boolean mLoopFlag = true;

    public ServerThread() {
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void run() {
        try {
            if (!adapter.isEnabled()) {
                adapter.enable();
            }
            serverSocket = adapter.listenUsingRfcommWithServiceRecord("FULLWAY", ConnectThread.PUBLIC_UUID);

            while (mLoopFlag) {

                Log.e("TAG", "监听中........;\n");
                socket = serverSocket.accept();
                BluetoothDevice bd = socket.getRemoteDevice();
                String address = bd.getAddress();
                String name = bd.getName();
                socket.close();
                socket = null;

                OttoManager.post(new FindDeviceOtto(name, address));
            }
            Log.d("ServerThread", "cancel");
            if (null != serverSocket)
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e1) {
                    Log.e("TAG", "关闭监听ServerSocket时出错;\n" + e1.toString());
                }
            if (null != socket) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    Log.e("TAG", "关闭监听Socket时出错;\n" + e1.toString());
                }
            }
        } catch (IOException e) {
            if (null != serverSocket)
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e1) {
                    Log.e("TAG", "关闭监听ServerSocket时出错;\n" + e1.toString());
                }
            if (null != socket) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    Log.e("TAG", "关闭监听Socket时出错;\n" + e1.toString());
                }
            }
            Log.e("TAG", "监听蓝牙设备的服务线程出错;\n" + e.toString());

        }
    }

    public void cancel() {
        mLoopFlag = false;
    }
}
