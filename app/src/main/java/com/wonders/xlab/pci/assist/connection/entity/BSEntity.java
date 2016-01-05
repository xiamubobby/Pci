package com.wonders.xlab.pci.assist.connection.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by hua on 15/11/9.
 */
@Table(name = "BSEntity")
public class BSEntity extends Model implements Serializable {
    private static final long serialVersionUID = 8000111713589185380L;

    @Column(name = "bloodSugar")
    private double bloodSugar;
    @Column(name = "measureTime")
    private long measureTime;

    public BSEntity() {
    }

    public BSEntity(long measureTime, double bloodSugar) {

        this.measureTime = measureTime;
        this.bloodSugar = bloodSugar;
    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public long getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(long measureTime) {
        this.measureTime = measureTime;
    }
}
