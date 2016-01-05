package com.wonders.xlab.pci.assist.connection.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by hua on 15/11/9.
 */
@Table(name = "BPEntity")
public class BPEntity extends Model implements Serializable {
    private static final long serialVersionUID = 1966262086867230897L;

    /**
     * 收缩压，高压
     */
    @Column(name = "systolicPressure")
    private int systolicPressure;
    @Column(name = "diastolicPressure")
    private int diastolicPressure;
    @Column(name = "heartRate")
    private int heartRate;
    @Column(name = "averagePressure")
    private int averagePressure;
    @Column(name = "date")
    private long date;

    public BPEntity() {
    }

    public BPEntity(long date, int systolicPressure, int diastolicPressure, int heartRate, int averagePressure) {
        this.date = date;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.averagePressure = averagePressure;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(int averagePressure) {
        this.averagePressure = averagePressure;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
