package com.wonders.xlab.pci.assist.connection.aamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;

import java.io.Serializable;

/**
 * Created by hua on 15/11/9.
 */
@Table(name = "BSAAModel")
public class BSAAModel extends Model implements Serializable {
    private static final long serialVersionUID = 8000111713589185380L;

    @Column(name = "bloodSugar")
    private double bloodSugar;
    @Column(name = "date")
    private long date;
    @Column(name = "timeIndex")
    private int timeIndex;

    public BSAAModel() {
    }

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
