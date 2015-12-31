package com.wonders.xlab.pci.assist.connection.base;


import com.wonders.xlab.pci.assist.connection.entity.BaseConnectionEntity;

import java.util.Calendar;

/**
 * Created by hua on 15/10/30.
 */
public abstract class DataRequestThread extends Thread {
    public static final String TAG = "RequestDataThread";

    private final long RETRY_TIME_INTERVAL = 1000;//ms

    private long lastRetryTime;//ms

    protected OnReceiveDataListener mOnReceiveDataListener;

    protected void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        mOnReceiveDataListener = onReceiveDataListener;
    }

    protected void retryRequest() {
        if (mOnReceiveDataListener != null) {
            mOnReceiveDataListener.retryRequest();
        }
    }

    /**
     * 连接失败
     */
    protected void requestDataFailed() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastRetryTime < RETRY_TIME_INTERVAL) {
            return;
        }
        lastRetryTime = currentTime;

        cancel();
        if (mOnReceiveDataListener != null) {
            mOnReceiveDataListener.onReceiveDataFailed();
        }
    }

    public interface OnReceiveDataListener {
        /**
         * 接收数据
         *
         * @param o
         */
        void onReceiveData(BaseConnectionEntity o);

        /**
         * 请求数据失败
         */
        void onReceiveDataFailed();

        void retryRequest();
    }

    public abstract void cancel();

}
