package com.wonders.xlab.patient.module.service.detail;

import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by natsuki on 16/5/9.
 */
public class ServiceDetailDataUnit {
    public ServiceDetailDataUnit() {}
    public ServiceDetailDataUnit(ServiceDetailEntity entity) {
        ServiceDetailEntity.RetValuesEntity rv = entity.getRet_values();
        this.title = rv.getTitle();
        this.description = rv.getDescription();
        this.price = rv.getPrice();
        this.organizationName = rv.getOrganizationName();
        this.organizationImageUrl = rv.getOrganizationImageUrl();
        this.organizationStatus = rv.getOrganizationStatus();
        this.bannerList = Observable.from(rv.getBannerList()).map(new Func1<ServiceDetailEntity.RetValuesEntity.BannerListEntity, Banner>() {
            @Override
            public Banner call(ServiceDetailEntity.RetValuesEntity.BannerListEntity bannerListEntity) {
                Banner ret = new Banner();
                ret.id = bannerListEntity.getId();
                ret.imageUrl = bannerListEntity.getImageUrl();
                ret.enable = bannerListEntity.isEnable();
                return ret;
            }
        }).toList().toBlocking().single();
        this.specificanList = Observable.from(rv.getServiceSpecifications()).map(new Func1<ServiceDetailEntity.RetValuesEntity.ServiceSpecificationsEntity, Specifican>() {
            @Override
            public Specifican call(ServiceDetailEntity.RetValuesEntity.ServiceSpecificationsEntity serviceSpecificationsEntity) {
                Specifican ret = new Specifican();
                ret.id = serviceSpecificationsEntity.getId();
                ret.count = serviceSpecificationsEntity.getCount();
                ret.name = serviceSpecificationsEntity.getName();
                ret.price = serviceSpecificationsEntity.getPrice();
                return ret;
            }
        }).toList().toBlocking().single();
    }
    private String title;
    private String description;
    private Float price;
    private String organizationName;
    private String organizationImageUrl;
    private String organizationStatus;
    private List<Banner> bannerList;
    private List<Specifican> specificanList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationImageUrl() {
        return organizationImageUrl;
    }

    public void setOrganizationImageUrl(String organizationImageUrl) {
        this.organizationImageUrl = organizationImageUrl;
    }

    public String getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(String organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Specifican> getSpecificanList() {
        return specificanList;
    }

    public void setSpecificanList(List<Specifican> specificanList) {
        this.specificanList = specificanList;
    }

    public class Banner {
        private int id;
        private String imageUrl;
        private boolean enable;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
    private class Specifican {
        private int id;
        private String name;
        private int price;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
