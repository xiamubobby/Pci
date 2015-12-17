package com.wonders.xlab.pci.module.task.bean;

/**
 * Created by hua on 15/12/17.
 */
public class WineBean {
    private long mTime;
    private String mValue;

    public WineBean(long time, String mValue) {
        this.mTime = time;
        this.mValue = mValue;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }
}
