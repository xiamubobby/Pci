package com.wonders.xlab.pci.module.home.bean;

/**
 * Created by hua on 15/12/14.
 */
public class TodayTaskBean extends HomeTaskBean {
    private String name;
    private String title;
    private long updateTime;

    @Override
    public int getItemLayout() {
        return HomeTaskBean.ITEM_TODAY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
