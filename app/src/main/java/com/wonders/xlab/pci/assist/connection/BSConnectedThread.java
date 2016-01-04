package com.wonders.xlab.pci.assist.connection;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.assist.connection.base.DataRequestThread;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.contec.jar.cmssxt.CmssxtDataJar;
import cn.com.contec.jar.cmssxt.DeviceCommand;
import cn.com.contec.jar.cmssxt.DevicePackManager;

/**
 * Created by hua on 15/10/26.
 * <p>
 * 血糖数据传输线程
 */
public class BSConnectedThread extends DataRequestThread {
    private BluetoothSocket mSocket;

    private final InputStream mInputStream;

    private final OutputStream mOutputStream;

    private boolean loop = true;

    public BSConnectedThread(BluetoothSocket socket) {
        super();

        mSocket = socket;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            if (mSocket != null) {
                tmpIn = mSocket.getInputStream();
                tmpOut = mSocket.getOutputStream();
            }

        } catch (IOException e) {
        }

        mInputStream = tmpIn;
        mOutputStream = tmpOut;
    }

    /**
     * 在删除数据成功后会继续循环请求数据
     */
    @Override
    public void run() {

        // Keep listening to the InputStream until an exception occurs
        DevicePackManager mPackManager = new DevicePackManager();

        byte[] readBuffer = new byte[1024];
        int readBytes;
        int result;
        boolean isNewDevice = false;

        //首先索要设备号
        byte[] command = DeviceCommand.command_ReadID();

        try {
            if (mOutputStream == null) {
                return;
            }
            mOutputStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (loop) {
            try {
                if (mSocket != null && mSocket.isConnected() && mInputStream != null) {
                    readBytes = mInputStream.read(readBuffer);
                    result = mPackManager.arrangeMessage(readBuffer, readBytes);

                    switch (result) {
                        case 1://成功接收数据
                            ArrayList<CmssxtDataJar> al = mPackManager.m_DeviceDatas;

                            List<BSEntity> bgEntities = parseBloodGlucose(al);
                            Log.d(TAG, "请求数据成功，一共" + bgEntities.size() + "条");
                            if (bgEntities.isEmpty()) {
                                Log.d(TAG, "接收到的数据为空，继续请求数据");
                                mOutputStream.write(DeviceCommand.command_requestData());
                            } else {
                                for (BSEntity bgEntity : bgEntities) {
                                    if (mOnReceiveDataListener != null) {
                                        mOnReceiveDataListener.onReceiveData(bgEntity);
                                    }
                                    OttoManager.post(bgEntity);
                                }
//                                Log.d(TAG, "接收到的数据不为空，删除数据");
//                                mOutputStream.write(DeviceCommand.command_delData());
                            }
                            break;
                        case 2://接收数据失败
                            break;
                        case 3://校正时间成功
                            Log.d(TAG, "校正时间成功");
                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 4://校正时间失败
                            Log.d(TAG, "校正时间失败");
                            if (isNewDevice) {
                                mOutputStream.write(DeviceCommand.command_VerifyTimeSS());
                            } else {
                                mOutputStream.write(DeviceCommand.command_VerifyTime());
                            }
                            break;
                        case 5://数据删除成功
                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 6://数据删除失败
                            mOutputStream.write(DeviceCommand.command_delData());
                            break;
                        case 7://无数据
                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 8://旧设备
                            Log.d(TAG, "旧设备");
                            mOutputStream.write(DeviceCommand.command_VerifyTime());
                            break;
                        case 9://新设备
                            Log.d(TAG, "新设备");
                            isNewDevice = true;

                            mOutputStream.write(DeviceCommand.command_VerifyTimeSS());
                            break;
                        default:
                            mOutputStream.write(DeviceCommand.command_requestData());
                    }

                } else {
                    requestDataFailed();
                }

            } catch (IOException e) {
                requestDataFailed();
            }

        }

        try {
            if (mInputStream != null) {
                mInputStream.close();
            }

            mOutputStream.close();
            cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param cmssxtDataJars
     */
    private synchronized List<BSEntity> parseBloodGlucose(ArrayList<CmssxtDataJar> cmssxtDataJars) {

        List<BSEntity> bgEntities = new ArrayList<>();

        for (CmssxtDataJar cmssxtDataJar : cmssxtDataJars) {
            BSEntity bgEntity = new BSEntity(DateUtil.parse(cmssxtDataJar.m_saveDate, "yyyy-MM-dd HH:mm:ss"), cmssxtDataJar.m_data);
            bgEntities.add(bgEntity);
        }
        return bgEntities;
    }

    @Override
    public void cancel() {
        loop = false;
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}