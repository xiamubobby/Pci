package com.wonders.xlab.patient.module.alldoctor;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.AllDoctorPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = AllDoctorModule.class)
public interface AllDoctorComponent {
    AllDoctorPresenter getAllDoctorPresenter();
}
