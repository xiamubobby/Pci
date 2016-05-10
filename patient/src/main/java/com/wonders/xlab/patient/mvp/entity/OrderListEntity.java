package com.wonders.xlab.patient.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by jimmy on 16/5/9.
 */
public class OrderListEntity extends BaseEntity{


    /**
     * content : [{"id":1,"orderId":"1_1_1462507195252","status":"有效","title":"健康套餐","price":1000,"specificationImageUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png","organizationName":"万达全程健康有限公司","organizationImageUrl":"http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png"}]
     * last : true
     * totalElements : 1
     * totalPages : 1
     * size : 20
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 1
     */

    private RetValuesBean ret_values;

    public RetValuesBean getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesBean ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesBean {
        private boolean last;
        private int totalElements;
        private int totalPages;
        private int size;
        private int number;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        /**
         * id : 1
         * orderId : 1_1_1462507195252
         * status : 有效
         * title : 健康套餐
         * price : 1000
         * specificationImageUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png
         * organizationName : 万达全程健康有限公司
         * organizationImageUrl : http://7xp6gb.com2.z0.glb.qiniucdn.com/pci/picture/pic2@2x.png
         */

        private List<ContentBean> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            private String id;
            private String orderId;
            private String status;
            private String title;
            private String price;
            private String specificationImageUrl;
            private String organizationName;
            private String organizationImageUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSpecificationImageUrl() {
                return specificationImageUrl;
            }

            public void setSpecificationImageUrl(String specificationImageUrl) {
                this.specificationImageUrl = specificationImageUrl;
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
        }
    }
}
