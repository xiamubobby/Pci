package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by WZH on 16/5/9.
 */
public interface ServiceDetailModelContract {
    interface Callback extends BaseModelListener {
        void showServiceDetail(ServiceDetailEntity entity);
    }

    interface Actions extends IBaseModel {
        void getServiceDetail(Long serviceId,Callback callback);
    }
}
