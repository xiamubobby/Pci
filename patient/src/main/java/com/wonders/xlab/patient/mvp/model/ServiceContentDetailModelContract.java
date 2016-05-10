package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.ServiceContentDetailEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by natsuki on 16/5/10.
 */
public interface ServiceContentDetailModelContract {
    interface Callback extends BaseModelListener {
        void showServiceContentDetail(ServiceContentDetailEntity entity);
    }
    interface Actions extends IBaseModel {
        void getServiceContentDetail(Long serviceId, Callback cb);
    }
}
