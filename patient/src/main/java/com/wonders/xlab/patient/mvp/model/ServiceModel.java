package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceEntity;

import javax.inject.Inject;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceModel extends PatientBaseModel<ServiceEntity> implements ServiceModelContract.Actions {

    private ServiceAPI serviceAPI;

    @Inject
    ServiceModel(ServiceAPI serviceAPI) {
        serviceAPI = serviceAPI;
    }

    @Override
    public void getServiceList(String patientId, int page, int pageSize, ServiceModelContract.Callback callback) {
//        request(mDoctorAPI.getAllDoctors(patientId, page, pageSize), new Callback<DoctorAllEntity>() {
//            @Override
//            public void onSuccess(DoctorAllEntity response) {
//                callback.onReceiveAllDoctorListSuccess(response.getRet_values());
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//                callback.onReceiveFailed(code, message);
//            }
//        });
        callback.onReceiveServiceListSuccess(new ServiceEntity.RetValuesEntity());
    }
}
