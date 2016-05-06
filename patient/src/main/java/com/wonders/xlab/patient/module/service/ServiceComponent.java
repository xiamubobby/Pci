package com.wonders.xlab.patient.module.service;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.alldoctor.AllDoctorModule;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenter;

import dagger.Component;

/**
 * Created by natsuki on 16/5/6.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = ServiceModule.class)
public interface ServiceComponent {
    ServicePresenter getServicePresenter();
}
