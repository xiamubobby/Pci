package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceModelContract {
    public interface Callback extends BaseModelListener {
        void onReceiveServiceListSuccess(ServiceListEntity.RetValuesEntity retValuesEntity);
    }

    public interface Actions extends IBaseModel {
        void getServiceList(Callback callback);
    }
}
