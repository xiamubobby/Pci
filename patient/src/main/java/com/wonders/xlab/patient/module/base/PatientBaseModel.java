package com.wonders.xlab.patient.module.base;


import com.wonders.xlab.patient.Constant;

import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by hua on 16/2/19.
 */
public abstract class PatientBaseModel<T extends BaseEntity> extends BaseModel<T> {

    @Override
    public String getBaseUrl() {
        return Constant.BASE_URL;
    }
}
