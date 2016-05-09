package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import javax.inject.Inject;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailModel extends PatientBaseModel<ServiceDetailEntity> implements ServiceDetailModelContract.Actions {
    private ServiceAPI mServiceAPI;

    @Inject
    ServiceDetailModel(ServiceAPI serviceAPI) {
        mServiceAPI = serviceAPI;
    }

    @Override
    public void getServiceDetail(Long serviceId, final ServiceDetailModelContract.Callback callback) {

        request(mServiceAPI.getServiceDetail(serviceId), new Callback<ServiceDetailEntity>() {
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
