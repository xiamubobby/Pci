package com.wonders.xlab.pci.module.task.bean;

/**
 * Created by hua on 15/12/16.
 */
public class BloodSugarBean {
    private float bloodSugar;
    private long time;

    public BloodSugarBean(float bloodSugar, long time) {
        this.bloodSugar = bloodSugar;
        this.time = time;
    }

    public float getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(float bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
