package com.wonders.xlab.pci.doctor.base;

import com.wonders.xlab.pci.doctor.Constant;

import im.hua.library.base.mvp.BaseModel;

/**
 * Created by hua on 16/2/19.
 */
public abstract class DoctorBaseModel extends BaseModel {
    @Override
    public String getBaseUrl() {
        return Constant.BASE_URL;
    }
}