package com.wonders.xlab.pci.assist.connection.entity;

import java.util.Date;

/**
 * Created by hua on 15/11/9.
 */
public class BPEntity extends BaseConnectionEntity {
    private static final long serialVersionUID = 1966262086867230897L;

    private int highPressure;
    private int lowPressure;
    private int pulseRate;
    private int averagePressure;

    public BPEntity(Date date, int highPressure, int lowPressure, int pulseRate, int averagePressure) {
        this.date = date;
        this.highPressure = highPressure;
        this.lowPressure = lowPressure;
        this.pulseRate = pulseRate;
        this.averagePressure = averagePressure;
    }

    public int getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(int highPressure) {
        this.highPressure = highPressure;
    }

    public int getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(int lowPressure) {
        this.lowPressure = lowPressure;
    }

    public int getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }

    public int getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(int averagePressure) {
        this.averagePressure = averagePressure;
    }

    @Override
    public String toString() {
        return "highPressure=" + highPressure + ":" + "lowPressure=" + lowPressure
                + "pulseRate=" + pulseRate + ":" + "averagePressure=" + averagePressure;
    }
}
