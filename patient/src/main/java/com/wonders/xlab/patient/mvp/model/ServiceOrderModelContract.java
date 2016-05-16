package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.ServiceOrderEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by natsuki on 16/5/10.
 */
public interface ServiceOrderModelContract {
    interface Callback extends BaseModelListener {
        void onOrderSuccessed(ServiceOrderEntity entity);
    }

    interface Actions extends IBaseModel {
        void orderService(Long serviceId, Callback callback);
    }
}
