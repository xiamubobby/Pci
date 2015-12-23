package com.wonders.xlab.pci.module.record.report.bean;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailBean {
    private String title;
    private List<ReportDetailImageBean> picUrlList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ReportDetailImageBean> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<ReportDetailImageBean> picUrlList) {
        this.picUrlList = picUrlList;
    }
}
