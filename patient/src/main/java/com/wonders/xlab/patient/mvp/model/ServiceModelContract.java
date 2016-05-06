package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveServiceListSuccess(ServiceEntity.RetValuesEntity retValuesEntity);
    }

    public interface Actions extends IBaseModel {
        void getServiceList(String serviceId, int page, int pageSize, Callback callback);
    }
}
