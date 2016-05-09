package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ServiceAPIN;
import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;

import javax.inject.Inject;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceModel extends PatientBaseModel<ServiceListEntity> implements ServiceModelContract.Actions {

    private ServiceAPIN mServiceAPIN;

    @Inject
    ServiceModel(ServiceAPIN serviceAPIN) {
        this.mServiceAPIN = serviceAPIN;
    }

    @Override
    public void getServiceList(final ServiceModelContract.Callback callback) {
        request(mServiceAPIN.listHealthService(), new Callback<ServiceListEntity>() {
            @Override
            public void onSuccess(ServiceListEntity response) {
                callback.onReceiveServiceListSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
