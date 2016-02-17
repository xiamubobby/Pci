package com.wonders.xlab.pci.assist.deviceconnection.aamodel;

import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntity;

import java.io.Serializable;

/**
 * Created by hua on 15/11/9.
 */
public class BSAAModel implements Serializable {
    private static final long serialVersionUID = 8000111713589185380L;

    private double bloodSugar;
    private long date;
    private int timeIndex;

    public BSAAModel(long date, double bloodSugar) {
        this.date = date;
        this.bloodSugar = bloodSugar;
    }

    public void setBSEntity(BSEntity entity) {
        setDate(entity.getDate());
        setTimeIndex(entity.getTimeIndex());
        setBloodSugar(entity.getBloodSugarValue());
    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(int timeIndex) {
        this.timeIndex = timeIndex;
    }
}
