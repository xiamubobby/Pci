package com.wonders.xlab.pci.module.record.monitor.bean;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryChildBean {
    private boolean isTitle;
    private long time;
    private String value;
    private String counts;

    public MedicineCategoryChildBean(boolean isTitle) {
        this.isTitle = isTitle;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}