package com.wonders.xlab.pci.doctor.module.base;

import com.wonders.xlab.pci.doctor.Constant;

import im.hua.library.base.mvp.BaseEntity;
import im.hua.library.base.mvp.BaseModel;

/**
 * Created by hua on 16/2/19.
 */
public abstract class DoctorBaseModel<T extends BaseEntity> extends BaseModel<T> {
    @Override
    public String getBaseUrl() {
        return Constant.BASE_URL;
    }
}
