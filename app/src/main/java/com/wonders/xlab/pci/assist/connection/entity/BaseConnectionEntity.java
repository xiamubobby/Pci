package com.wonders.xlab.pci.assist.connection.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by hua on 15/11/9.
 */
public class BaseConnectionEntity implements Serializable {
    private static final long serialVersionUID = -2695287313135392837L;

    Date date; // 测量时间
    long measureTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(long measureTime) {
        this.measureTime = measureTime;
    }
}
