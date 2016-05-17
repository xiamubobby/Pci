package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.service.ServiceListCellDataUnit;
import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;
import com.wonders.xlab.patient.mvp.model.ServiceModel;
import com.wonders.xlab.patient.mvp.model.ServiceModelContract;

import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import rx.Observable;
import rx.functions.Func1;

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
    public void getAllServices(boolean isRefresh) {
        serviceModel.getServiceList(new ServiceModelContract.Callback() {
            @Override
            public void onReceiveFailed(int code, String message) {
                showError(serviceListener, code, message);
            }

            @Override
            public void onReceiveServiceListSuccess(ServiceListEntity.RetValuesEntity retValuesEntity) {
                final List<ServiceListCellDataUnit> units = Observable.from(retValuesEntity.getContent()).map(new Func1<ServiceListEntity.RetValuesEntity.Content, ServiceListCellDataUnit>() {
                    @Override
                    public ServiceListCellDataUnit call(ServiceListEntity.RetValuesEntity.Content content) {
                        return new ServiceListCellDataUnit(content);
                    }
                }).toList().toBlocking().single();
                serviceListener.showAllServiceList(units);
                serviceListener.hideLoading();
            }
        });
    }
}
