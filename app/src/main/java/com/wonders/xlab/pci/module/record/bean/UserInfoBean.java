package com.wonders.xlab.pci.module.record.bean;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoBean {
    private int viewType;
    private String label;
    private String value;

    public UserInfoBean(int viewType, String label, String value) {
        this.viewType = viewType;
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
