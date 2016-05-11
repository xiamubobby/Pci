package com.wonders.xlab.patient.module.healthrecord;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.HealthRecordPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/11.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = HealthRecordModule.class)
public interface HealthRecordComponent {
    HealthRecordPresenter getHealthRecordPresenter();
}
