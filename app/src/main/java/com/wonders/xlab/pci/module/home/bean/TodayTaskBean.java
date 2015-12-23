package com.wonders.xlab.pci.module.home.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by hua on 15/12/14.
 */
@Table(name = "TodayTaskBean")
public class TodayTaskBean extends HomeTaskBean {

    @Column(name = "portrait")
    private String portrait;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "updateTime")
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
