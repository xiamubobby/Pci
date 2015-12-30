package com.wonders.xlab.pci.assist.connection.entity;

import java.util.Date;

/**
 * Created by hua on 15/11/9.
 */
public class BSEntity extends BaseConnectionEntity {
    private static final long serialVersionUID = 8000111713589185380L;

    private double bloodSugar;

    public BSEntity(Date date, double bloodSugar) {

        this.date = date;
        this.bloodSugar = bloodSugar;
    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }
}
