package com.wonders.xlab.pci.assist.connection.aamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

    public BSAAModel() {
    }

    public BSAAModel(long date, double bloodSugar) {

        this.date = date;
        this.bloodSugar = bloodSugar;
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
