package com.wonders.xlab.pci.doctor.module.otto;

/**
 * Created by hua on 16/4/5.
 * 改变主界面底部的未读消息数标记
 */
public class MainBottomUnreadNotifyCountOtto {
    private String imGroupId;

    public MainBottomUnreadNotifyCountOtto(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }
}
