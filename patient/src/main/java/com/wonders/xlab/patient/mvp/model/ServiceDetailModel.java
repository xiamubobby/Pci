package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ServiceAPIN;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import javax.inject.Inject;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailModel extends PatientBaseModel<ServiceDetailEntity> implements ServiceDetailModelContract.Actions {
    private ServiceAPIN mServiceAPIN;

    @Inject
    ServiceDetailModel(ServiceAPIN serviceAPIN) {
        mServiceAPIN = serviceAPIN;
    }

    @Override
    public void getServiceDetail(Long serviceId, final ServiceDetailModelContract.Callback callback) {

        request(mServiceAPIN.getServiceDetail(serviceId), new Callback<ServiceDetailEntity>() {
            @Override
            public void onSuccess(ServiceDetailEntity response) {
                ServiceDetailEntity.RetValuesEntity retValues = response.getRet_values();
                if (null == retValues) {
                    callback.onReceiveFailed(-1, "登录失败，请重试！");
                    return;
                }
                callback.showServiceDetail(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });

    }
}
