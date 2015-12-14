package com.wonders.xlab.pci.module.record.bean;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailBean {
    private String title;
    private List<String> picUrlList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }
}
