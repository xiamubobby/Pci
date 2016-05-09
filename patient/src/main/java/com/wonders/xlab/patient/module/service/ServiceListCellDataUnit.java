package com.wonders.xlab.patient.module.service;

import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceListCellDataUnit {//u can refactor this to ServiceBean if u like.
    public ServiceListCellDataUnit(){}
    public ServiceListCellDataUnit(ServiceListEntity.RetValuesEntity.Content content) {
        this.id = content.getId();
        this.provider = content.getOrganizationName();
        this.title = content.getTitle();
        this.brief = content.getDescription();
        this.imgURL = content.getImageUrl();
        this.price = content.getPrice();
        this.providerImgURL = content.getOrganizationImageUrl();
    }
    private Long id;
    private String provider;
    private String title;
    private String brief;
    private Float price;
    private String imgURL;
    private String providerImgURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getProviderImgURL() {
        return providerImgURL;
    }

    public void setProviderImgURL(String providerImgURL) {
        this.providerImgURL = providerImgURL;
    }
}
