package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.model.AllDoctorModel;
import com.wonders.xlab.patient.mvp.model.AllDoctorModelContract;
import com.wonders.xlab.patient.mvp.model.ServiceModel;
import com.wonders.xlab.patient.mvp.model.ServiceModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServicePresenter extends BasePagePresenter implements ServicePresenterContract.Actions {

    private ServicePresenterContract.ViewListener serviceListener;
    private ServiceModelContract.Actions serviceModel;
    @Inject
    ServicePresenter(ServicePresenterContract.ViewListener listener, ServiceModel model) {
        serviceListener = listener;
        serviceModel = model;
        addModel(serviceModel);
    }

    @Override
    public void getAllServices(String serviceId, boolean isRefresh) {

    }
}
