package com.wonders.xlab.pci.assist.connection;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.contec.jar.contec08a.DeviceCommand;
import com.contec.jar.contec08a.DevicePackManager;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.assist.connection.aamodel.BPAAModel;
import com.wonders.xlab.pci.assist.connection.base.DataRequestThread;
import com.wonders.xlab.pci.assist.connection.entity.BPEntity;
import com.wonders.xlab.pci.assist.connection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.connection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.connection.otto.RequestDataFailed;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by hua on 15/10/26.
 * <p/>
 * 血压数据传输线程
 */
public class BPConnectedThread extends DataRequestThread {

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

    private DevicePackManager mPackManager;

    /**
     * 由于需要不断获取数据，所以在成功删除血压数据后，再继续发送获取数据命令
     */
    @Override
    public void run() {

        // Keep listening to the InputStream until an exception occurs
        mPackManager = new DevicePackManager();

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
                            if (mOp == timeNotice) {
                                if (BuildConfig.DEBUG) Log.d(TAG, "发送将要校正时间命令");
                                pType = 3;
                                mOutputStream.write(DeviceCommand.correct_time_notice);
                            } else if (mOp == requestData) {
                                if (BuildConfig.DEBUG) Log.d(TAG, "发送获取数据命令");
                                pType = 1;
                                mOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
                            } else {
                                if (BuildConfig.DEBUG) Log.d(TAG, "发送删除数据命令");
                                pType = 5;
                                mOutputStream.write(DeviceCommand.DELETE_BP());
                            }
//                        }
                            break;
                        case 0x30://可以发送时间校正命令
                            if (BuildConfig.DEBUG) Log.d(TAG, "发送时间校正命令");
                            pType = 3;
                            mOutputStream.write(DeviceCommand.Correct_Time());
                            break;
                        case 0x31://不可以发送时间校正命令 49
                            if (BuildConfig.DEBUG) Log.d(TAG, "不可进行时间校正");
                            if (BuildConfig.DEBUG) Log.d(TAG, "不可进行时间校正");
                            requestDataFailed();
                            break;
                        case 0x40://校正设备时间成功 64
                            if (BuildConfig.DEBUG) Log.d(TAG, "校正设备时间成功");
                            mOp = requestData;
                            pType = 0;
                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            break;
                        case 0x41://校正设备时间失败 65
                            if (BuildConfig.DEBUG) Log.d(TAG, "校正设备时间失败");
                            requestDataFailed();
                            break;
                        case 0x46://血压数据接收完毕 70
                            if (BuildConfig.DEBUG) Log.d(TAG, "接收到血压数据");

                            ArrayList<byte[]> data = mPackManager.mDeviceData.mData_blood;

                            List<BPEntity> bpEntities = praiseBloodPressure(data);

                            if (bpEntities.isEmpty()) {
                                if (BuildConfig.DEBUG) Log.d(TAG, "接收到的数据为空");
                                cancel();
                                /*mOp = requestData;
                                pType = 0;
                                mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());*/
                                OttoManager.post(new EmptyDataOtto(true));
                            } else {
                                if (BuildConfig.DEBUG) Log.d(TAG, "接收到的数据不为空，删除数据");
                                mOp = deleteData;
                                pType = 0;
                                mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            }

                            break;
                        case 0x50://血压删除成功 80
                            //继续请求血压数据
                            if (BuildConfig.DEBUG) Log.d(TAG, "删除数据成功");
//                            mOp = requestData;
//                            pType = 0;
//                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            cancel();
                            break;
                        case 0x51://血压删除失败 81
                            if (BuildConfig.DEBUG) Log.d(TAG, "删除数据失败");
//                            mOp = requestData;
//                            pType = 0;
//                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                            cancel();
                            break;
                        default:
                            if (BuildConfig.DEBUG) Log.d(TAG, "default");
//                            if (BuildConfig.DEBUG) Log.d(TAG, "默认发送请求数据指令");
//                            mOp = requestData;
//                            pType = 0;
//                            mOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                    }

                } else {
                    if (BuildConfig.DEBUG) Log.d(TAG, "重开数据接受线程");
//                    requestDataFailed();
                    cancel();
                    OttoManager.post(new EmptyDataOtto(true));
                }

            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.d(TAG, e.getLocalizedMessage() + " " + e.getMessage());
//                requestDataFailed();
                cancel();
                OttoManager.post(new RequestDataFailed("读取血压数据失败，请先测量血压，并确保血压仪开启，然后重新同步数据"));
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
        if (BuildConfig.DEBUG) Log.d(TAG, "关闭连接");
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
            int minute = buffer[9];// 分
            int sec = buffer[10];// 秒

            String dateStr = "20" + year + "-" + month + "-" + day + " " + hour
                    + ":" + minute + ":" + sec;
            Date date = DateUtil.parse(dateStr, "yyyy-MM-dd HH:mm:ss");

            BPAAModel BPAAModel = new BPAAModel(date == null ? Calendar.getInstance().getTimeInMillis() : date.getTime(),
                    hp,
                    lp,
                    pulseRate,
                    ap);
            //缓存数据
            BPAAModel.save();

            BPEntity bpEntity = new BPEntity();
            bpEntity.setBPModel(BPAAModel);

            bpEntities.add(bpEntity);
        }

        if (bpEntities.size() > 0) {
            Collections.sort(bpEntities, new Comparator<BPEntity>() {
                @Override
                public int compare(BPEntity lhs, BPEntity rhs) {
                    long tmp = lhs.getDate() - rhs.getDate();
                    return tmp > 0 ? -1 : (tmp == 0 ? 0 : 1);
                }
            });
            OttoManager.post(new BPEntityList(bpEntities));
        }
        return bpEntities;
    }
}