package com.wonders.xlab.pci.module.record.monitor.bean;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryBean implements ParentListItem {
    private List<MedicineCategoryChildBean> mChildBeanList;
    private String title;
    @Override
    public List<MedicineCategoryChildBean> getChildItemList() {
        return mChildBeanList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public List<MedicineCategoryChildBean> getChildBeanList() {
        return mChildBeanList;
    }

    public void setChildBeanList(List<MedicineCategoryChildBean> childBeanList) {
        mChildBeanList = childBeanList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
