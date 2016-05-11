package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.entity.ServiceOrderEntity;

import javax.inject.Inject;

/**
 * Created by natsuki on 16/5/10.
 */
public class ServiceOrderModel extends PatientBaseModel implements ServiceOrderModelContract.Actions {
    private ServiceAPI mServiceAPI;

    @Inject
    public ServiceOrderModel(ServiceAPI serviceAPI) {
        this.mServiceAPI = serviceAPI;
    }

    @Override
    public void orderService(Long serviceId, final ServiceOrderModelContract.Callback callback) {
        request(mServiceAPI.generateServiceOrder(Long.parseLong(AIManager.getInstance().getPatientId()), serviceId), new Callback<ServiceOrderEntity>() {
            @Override
            public void onSuccess(ServiceOrderEntity response) {
                callback.onOrderSuccessed(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
