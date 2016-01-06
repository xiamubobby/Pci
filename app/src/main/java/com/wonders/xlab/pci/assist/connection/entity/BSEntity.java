package com.wonders.xlab.pci.assist.connection.entity;

import com.wonders.xlab.pci.assist.connection.aamodel.BSAAModel;

/**
 * Created by hua on 15/11/9.
 */
public class BSEntity {

    private double bloodSugar;
    private long date;

    public BSEntity() {
    }

    public BSEntity(long date, double bloodSugar) {

        this.date = date;
        this.bloodSugar = bloodSugar;
    }

    public void setBSModel(BSAAModel bsAAModel) {
        setBloodSugar(bsAAModel.getBloodSugar());
        setDate(bsAAModel.getDate());
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
}
