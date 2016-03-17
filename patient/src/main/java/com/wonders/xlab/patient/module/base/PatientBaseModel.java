package com.wonders.xlab.patient.module.base;


import com.wonders.xlab.patient.Constant;

import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by hua on 16/2/19.
 */
public abstract class PatientBaseModel<T extends BaseEntity> extends BaseModel<T> {
    private int pageIndex = -1;
    private int size = 10;
    private boolean isLast = false;
    private boolean isFirst = true;

    protected void setFirst(boolean first) {
        isFirst = first;
    }

    protected void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    protected void setLast(boolean last) {
        isLast = last;
    }

    protected int getPageIndex() {
        return pageIndex;
    }

    protected boolean isLast() {
        return isLast;
    }

    protected boolean isFirst() {
        return isFirst;
    }

    protected int getSize() {
        return size;
    }

    @Override
    public String getBaseUrl() {
        return Constant.BASE_URL;
    }
}