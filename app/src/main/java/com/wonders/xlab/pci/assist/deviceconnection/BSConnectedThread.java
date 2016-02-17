package com.wonders.xlab.pci.assist.deviceconnection;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.assist.deviceconnection.base.DataRequestThread;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntity;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.pci.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.deviceconnection.otto.RequestDataFailed;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.com.contec.jar.cmssxt.CmssxtDataJar;
import cn.com.contec.jar.cmssxt.DeviceCommand;
import cn.com.contec.jar.cmssxt.DevicePackManager;

/**
 * Created by hua on 15/10/26.
 * <p/>
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

        } catch (IOException ignored) {
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
                            if (bgEntities.isEmpty()) {
                                if (BuildConfig.DEBUG) Log.d(TAG, "接收到的数据为空");
                                cancel();
                                OttoManager.post(new EmptyDataOtto(true));
                            } else {
                                if (BuildConfig.DEBUG) Log.d(TAG, "接收到的数据不为空，删除数据");
                                mOutputStream.write(DeviceCommand.command_delData());
                            }
                            break;
                        case 2://接收数据失败
                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 3://校正时间成功
                            if (BuildConfig.DEBUG) Log.d(TAG, "校正时间成功");
                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 4://校正时间失败
                            if (BuildConfig.DEBUG) Log.d(TAG, "校正时间失败");
                            if (isNewDevice) {
                                mOutputStream.write(DeviceCommand.command_VerifyTimeSS());
                            } else {
                                mOutputStream.write(DeviceCommand.command_VerifyTime());
                            }
                            break;
                        case 5://数据删除成功
                            if (BuildConfig.DEBUG) Log.d(TAG, "数据删除成功");
                            cancel();
//                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 6://数据删除失败
                            if (BuildConfig.DEBUG) Log.d(TAG, "数据删除失败，重新请求数据");
                            cancel();
//                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 7://无数据
                            cancel();
                            OttoManager.post(new EmptyDataOtto(true));
//                            mOutputStream.write(DeviceCommand.command_requestData());
                            break;
                        case 8://旧设备
                            if (BuildConfig.DEBUG) Log.d(TAG, "旧设备");
                            mOutputStream.write(DeviceCommand.command_VerifyTime());
                            break;
                        case 9://新设备
                            if (BuildConfig.DEBUG) Log.d(TAG, "新设备");
                            isNewDevice = true;

                            mOutputStream.write(DeviceCommand.command_VerifyTimeSS());
                            break;
                        default:
                            mOutputStream.write(DeviceCommand.command_requestData());
                    }

                } else {
                    cancel();
                    OttoManager.post(new EmptyDataOtto(true));
//                    requestDataFailed();
                }

            } catch (IOException e) {
                cancel();
                OttoManager.post(new RequestDataFailed(""));//读取血糖数据失败，请先测量血糖，并确保血糖仪开启，然后重新同步数据
//                requestDataFailed();
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

    private Calendar mCalendar = Calendar.getInstance();

    /**
     * @param cmssxtDataJars
     */
    private synchronized List<BSEntity> parseBloodGlucose(ArrayList<CmssxtDataJar> cmssxtDataJars) {

        List<BSEntity> bgEntities = new ArrayList<>();

        for (CmssxtDataJar cmssxtDataJar : cmssxtDataJars) {
            Date date = DateUtil.parse(cmssxtDataJar.m_saveDate, "yyyy-MM-dd HH:mm:ss");

            BSEntity bsEntity = new BSEntity();
            bsEntity.setDate(date == null ? Calendar.getInstance().getTimeInMillis() : date.getTime());
            bsEntity.setBloodSugarValue(cmssxtDataJar.m_data);

            mCalendar.setTimeInMillis(bsEntity.getDate());
            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);

            if (hour > 0 && hour <= 8) {
                bsEntity.setTimeIndex(0);
            } else if (hour > 8 && hour <= 10) {
                bsEntity.setTimeIndex(1);
            } else if (hour > 10 && hour <= 12) {
                bsEntity.setTimeIndex(2);
            } else if (hour > 12 && hour <= 14) {
                bsEntity.setTimeIndex(3);
            } else if (hour > 14 && hour <= 17) {
                bsEntity.setTimeIndex(4);
            } else if (hour > 17 && hour <= 20) {
                bsEntity.setTimeIndex(5);
            } else {
                bsEntity.setTimeIndex(6);
            }

            bgEntities.add(bsEntity);

        }
        if (bgEntities.size() > 0) {
            Collections.sort(bgEntities, new Comparator<BSEntity>() {
                @Override
                public int compare(BSEntity lhs, BSEntity rhs) {
                    long tmp = lhs.getDate() - rhs.getDate();
                    return tmp > 0 ? -1 : (tmp == 0 ? 0 : 1);
                }
            });
            OttoManager.post(new BSEntityList(bgEntities));
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