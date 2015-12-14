package com.wonders.xlab.pci.module.home.bean;

/**
 * Created by hua on 15/12/14.
 */
public class YesterdayTaskBean extends HomeTaskBean {
    private String portrait;
    private String name;
    private String title;
    private String content;
    private long updateTime;

    @Override
    public int getItemLayout() {
        return HomeTaskBean.ITEM_YESTERDAY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
