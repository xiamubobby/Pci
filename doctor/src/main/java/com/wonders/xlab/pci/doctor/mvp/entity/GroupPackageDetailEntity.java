package com.wonders.xlab.pci.doctor.mvp.entity;

import java.util.List;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by hua on 16/4/11.
 */
public class GroupPackageDetailEntity extends BaseEntity {

    /**
     * node : {"doctorPackageId":1,"price":200,"unitName":"元/月","content":"包月套餐包月套餐包月套餐包月套餐","unitTitle":"价格","publishType":1}
     * price : [{"id":1,"number":1},{"id":2,"number":10},{"id":3,"number":50},{"id":4,"number":100},{"id":5,"number":200}]
     */

    private RetValuesEntity ret_values;

    public RetValuesEntity getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity {
        /**
         * doctorPackageId : 1
         * price : 200
         * unitName : 元/月
         * content : 包月套餐包月套餐包月套餐包月套餐
         * unitTitle : 价格
         * publishType : 1
         */

        private NodeEntity node;
        /**
         * id : 1
         * number : 1
         */

        private List<PriceEntity> price;

        public NodeEntity getNode() {
            return node;
        }

        public void setNode(NodeEntity node) {
            this.node = node;
        }

        public List<PriceEntity> getPrice() {
            return price;
        }

        public void setPrice(List<PriceEntity> price) {
            this.price = price;
        }

        public static class NodeEntity {
            private int doctorPackageId;
            private int price;
            private String unitName;
            private String content;
            private String unitTitle;
            private int publishType;

            public int getDoctorPackageId() {
                return doctorPackageId;
            }

            public void setDoctorPackageId(int doctorPackageId) {
                this.doctorPackageId = doctorPackageId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUnitTitle() {
                return unitTitle;
            }

            public void setUnitTitle(String unitTitle) {
                this.unitTitle = unitTitle;
            }

            public int getPublishType() {
                return publishType;
            }

            public void setPublishType(int publishType) {
                this.publishType = publishType;
            }
        }

        public static class PriceEntity {
            private int id;
            private int number;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }
}
