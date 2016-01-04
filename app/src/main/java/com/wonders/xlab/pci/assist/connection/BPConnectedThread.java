package com.wonders.xlab.pci.assist.connection;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.contec.jar.contec08a.DeviceCommand;
import com.contec.jar.contec08a.DevicePackManager;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.assist.connection.base.DataRequestThread;
import com.wonders.xlab.pci.assist.connection.entity.BPEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/10/26.
 * <p>
 * 血压数据传输线程
 */
public class BPConnectedThread extends DataRequestThread {
//    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothSocket mSocket;

    private final InputStream mInputStream;

    private final OutputStream mOutputStream;

    private boolean loop = true;

    public BPConnectedThread(BluetoothSocket socket) {
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
     * 由于需要不断获取数据，所以在成功删除血压数据后，再继续发送获取数据命令
     */
    @Override
    public void run() {

        // Keep listening to the InputStream until an exception occurs
        DevicePackManager mPackManager = new DevicePackManager();

        byte[] readBuffer = new byte[1024];
        int readBytes;
        byte result;

        byte[] command = DeviceCommand.REQUEST_HANDSHAKE();

        try {
            if (mOutputStream == null) {
                return;
            }
            mOutputStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 0:表示处理与握手信息相关的数据。
         * 1:表示处理血压信息数据。
         * 2:表示处理血氧数据
         * 3:表示处理与时间相关的数据。例如:发送完通信流程中 II、III 命令后,处理其返回数据时,需传入 3。
         * 5:表示删除数据
         */
        int pType = 0;

        int timeNotice = 0;
        int correctTime = 1;
        int requestData = 2;
        int deleteData = 3;

        int mOp = timeNotice;

        while (loop) {
            try {
                if (mSocket != null && mSocket.isConnected() && mInputStream != null) {
                    readBytes = mInputStream.read(readBuffer);
                    result = mPackManager.arrangeMessage(readBuffer, readBytes, pType);
                    switch (result) {
                        case 0x48://握手成功 72
                            //设置命令类型
                            Log.d(TAG, "握手成功");

                            if (mOp == timeNotice) {
                                Log.d(TAG, "发送将要校正时间命令");
                                pType = 3;
                                mOutputStream.write(DeviceCommand.correct_time_notice);
                            } else if (mOp == requestData) {
                                Log.d(TAG, "发送获取数据命令");
                                pType = 1;
                                mOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
                            } else {
                                Log.d(TAG, "发送删除数据命令");
                                pType = 5;
//                                mOutputStream.write(DeviceCommand.DELETE_BP());
                            }
//                            else if (mOp == correctTime) {
//                                Log.d(TAG, "发送时间校正命令");
//                                pType = 3;
//                                mOutputStream.write(DeviceCommand.Correct_Time());
//                            }
                            break;
                        case 0x30://可以发送时间校正命令
                            Log.d(TAG, "可以进行时间校正");
//                            mOp = correctTime;
//                            pType = 0;
//                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            Log.d(TAG, "发送时间校正命令");
                            pType = 3;
                            mOutputStream.write(DeviceCommand.Correct_Time());
                            break;
                        case 0x31://不可以发送时间校正命令 49
                            Log.e(TAG, "不可进行时间校正");
                            requestDataFailed();
                            break;
                        case 0x40://校正设备时间成功 64
                            Log.d(TAG, "校正设备时间成功");
                            mOp = requestData;
                            pType = 0;
                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            break;
                        case 0x41://校正设备时间失败 65
                            Log.e(TAG, "校正设备时间失败");
                            requestDataFailed();
                            break;
                        case 0x46://血压数据接收完毕 70
                            Log.d(TAG, "接收到血压数据");

                            ArrayList<byte[]> data = mPackManager.mDeviceData.mData_blood;

                            List<BPEntity> bpEntities = praiseBloodPressure(data);

                            if (bpEntities.isEmpty()) {
                                Log.d(TAG, "接收到的数据为空，继续请求数据");
                                mOp = requestData;
                                pType = 0;
                                mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            } else {
                                Log.d(TAG, "接收到的数据不为空，删除数据");
                                for (BPEntity entity : bpEntities) {
                                    if (mOnReceiveDataListener != null) {
                                        mOnReceiveDataListener.onReceiveData(entity);
                                    }
                                }
//                                mOp = deleteData;
//                                pType = 0;
//                                mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            }

                            break;
                        case 0x50://血压删除成功 80
                            //继续请求血压数据
                            Log.d(TAG, "删除数据成功");
                            mOp = requestData;
                            pType = 0;
                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            break;
                        case 0x51://血压删除失败 81
                            Log.d(TAG, "删除数据失败，尝试重新删除");
                            mOp = deleteData;
                            pType = 0;
                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            break;
                        default:
//                            Log.d(TAG, "默认发送请求数据指令");
//                            mOp = requestData;
//                            pType = 0;
//                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                    }

                } else {
                    Log.d(TAG, "重开数据接受线程");
                    requestDataFailed();
                }

            } catch (IOException e) {
                Log.d(TAG, e.getLocalizedMessage() + " " + e.getMessage());
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

    @Override
    public void cancel() {
        Log.d(TAG, "关闭连接");
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

    /**
     * 血压数据处理
     *
     * @param data
     */
    private synchronized List<BPEntity> praiseBloodPressure(List<byte[]> data) {
        Log.d(TAG, "开始解析血压数据：一共" + data.size() + "条");
        List<BPEntity> bpEntities = new ArrayList<>();

        for (byte[] buffer : data) {
            byte[] array = {buffer[1], buffer[0]};
            ByteBuffer hpb = ByteBuffer.wrap(array);
            hpb.order(ByteOrder.LITTLE_ENDIAN);

            int hp = hpb.getShort();

            int lp = (int) buffer[2] & 0xFF;// 低压
            int pulseRate = (int) buffer[3] & 0xFF;// 脉率
            int ap = buffer[4];// 平均压
            int year = buffer[5];// 年份低两位
            int month = buffer[6];// 月
            int day = buffer[7];// 日
            int hour = buffer[8];// 时
            int sec = buffer[9];// 分
            int minute = buffer[10];// 秒

            String dateStr = "20" + year + "-" + month + "-" + day + " " + hour
                    + ":" + minute + ":" + sec;
            Log.d(TAG, dateStr);
            Log.d(TAG, "ap:" + ap);
            Log.d(TAG, "hp:" + hp);
            Log.d(TAG, "lp:" + lp);
            //TODO
            BPEntity bpEntity = new BPEntity(DateUtil.parse(dateStr, "yyyy-MM-dd HH:mm:ss"),
                    hp,
                    lp,
                    pulseRate,
                    ap);

            bpEntities.add(bpEntity);

            OttoManager.post(bpEntity);
        }

        return bpEntities;
    }
}