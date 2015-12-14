package com.wonders.xlab.pci.module.record.bean;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoBean {
    private String label;
    private String value;

    public UserInfoBean(String label, String value) {
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
}
