package com.wonders.xlab.patient.module.home.bean;

/**
 * Created by hua on 16/4/5.
 */
public class HomeBannerBean {
    private String title;
    private String imageUrl;
    private String linkUrl;

    public HomeBannerBean(String title, String imageUrl, String linkUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
