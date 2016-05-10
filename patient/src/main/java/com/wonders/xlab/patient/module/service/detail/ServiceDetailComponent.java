package com.wonders.xlab.patient.module.service.detail;


import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.ServiceDetailPresenter;

import dagger.Component;

/**
 * Created by WZH on 16/5/9.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = ServiceDetailModule.class)
public interface ServiceDetailComponent {
    ServiceDetailPresenter getServiceDetailPresenter();
}
