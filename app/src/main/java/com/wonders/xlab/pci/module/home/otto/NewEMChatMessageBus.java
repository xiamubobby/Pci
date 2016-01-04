package com.wonders.xlab.pci.module.home.otto;

/**
 * Created by hua on 15/12/23.
 */
public class NewEMChatMessageBus {
    private String title;
    private long time;

    public NewEMChatMessageBus() {
    }

    public NewEMChatMessageBus(String title, long time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
