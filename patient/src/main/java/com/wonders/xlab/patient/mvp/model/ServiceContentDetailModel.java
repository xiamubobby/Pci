package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.entity.ServiceContentDetailEntity;

import javax.inject.Inject;

/**
 * Created by natsuki on 16/5/10.
 */
public class ServiceContentDetailModel extends PatientBaseModel implements ServiceContentDetailModelContract.Actions {

    private ServiceAPI serviceAPI;
    @Inject
    public ServiceContentDetailModel(ServiceAPI api) {serviceAPI = api;}

    @Override
    public void getServiceContentDetail(Long serviceId, final ServiceContentDetailModelContract.Callback callback) {
        request(serviceAPI.getServiceContentDetail(serviceId), new Callback<ServiceContentDetailEntity>(){

            @Override
            public void onSuccess(ServiceContentDetailEntity response) {
                callback.showServiceContentDetail(response);
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
    }
}
