package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailEntity extends BaseEntity {

    /**
     * bannerList : [{"id":1,"imageUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png","enable":true},{"id":2,"imageUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png","enable":true}]
     * title : 健康套餐
     * description : 健康套餐描述
     * price : 1000
     * serviceSpecifications : [{"id":1,"name":"成人健康管理服务套餐（尊享卡）","price":1000,"count":5},{"id":2,"name":"成人健康管理服务套餐（尊享卡）","price":1000,"count":5}]
     * organizationName : 万达全程健康有限公司
     * organizationImageUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png
     * organizationStatus : 认证中
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        private String title;
        private String description;
        private int price;
        private String organizationName;
        private String organizationImageUrl;
        private String organizationStatus;
        /**
         * id : 1
         * imageUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png
         * enable : true
         */

        private List<BannerListEntity> bannerList;
        /**
         * id : 1
         * name : 成人健康管理服务套餐（尊享卡）
         * price : 1000
         * count : 5
         */

        private List<ServiceSpecificationsEntity> serviceSpecifications;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public List<BannerListEntity> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListEntity> bannerList) {
            this.bannerList = bannerList;
        }

        public List<ServiceSpecificationsEntity> getServiceSpecifications() {
            return serviceSpecifications;
        }

        public void setServiceSpecifications(List<ServiceSpecificationsEntity> serviceSpecifications) {
            this.serviceSpecifications = serviceSpecifications;
        }

        public static class BannerListEntity {
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

        public static class ServiceSpecificationsEntity {
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
}
