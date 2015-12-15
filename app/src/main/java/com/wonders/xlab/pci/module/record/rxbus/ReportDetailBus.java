package com.wonders.xlab.pci.module.record.rxbus;

/**
 * Created by hua on 15/12/15.
 */
public class ReportDetailBus {
    private String picUrl;
    private String picName;

    public ReportDetailBus(String picUrl, String picName) {
        this.picUrl = picUrl;
        this.picName = picName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
}
