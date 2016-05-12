package com.wonders.xlab.patient.base;


import android.util.Log;

import com.wonders.xlab.patient.BuildConfig;
import com.wonders.xlab.patient.Constant;

import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by hua on 16/2/19.
 */
public abstract class PatientBaseModel<T extends BaseEntity> extends BaseModel<T> {

    @Override
    public String getBaseUrl() {
        String endPoint = BuildConfig.DEBUG ? Constant.BASE_URL_DEBUG : Constant.BASE_URL;
        if (BuildConfig.DEBUG) Log.d("PatientBaseModel", endPoint);
        return endPoint;
    }
}
