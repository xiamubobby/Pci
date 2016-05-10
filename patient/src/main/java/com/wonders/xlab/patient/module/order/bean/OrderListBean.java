package com.wonders.xlab.patient.module.order.bean;

/**
 * Created by jimmy on 16/5/9.
 */
public class OrderListBean {

    /**
     * 订单id
     */
    private String id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单名称
     */
    private String title;
    /**
     * 订单价格
     */
    private String price;
    /**
     * 规格图片
     */
    private String specificationImageUrl;
    /**
     * 组织名称
     */
    private String organizationName;
    /**
     * 组织图片
     */
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
