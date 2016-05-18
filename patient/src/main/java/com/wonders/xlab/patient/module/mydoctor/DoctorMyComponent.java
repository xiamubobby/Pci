package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by hua on 16/5/18.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = DoctorMyModule.class)
public interface DoctorMyComponent {
    DoctorMyPresenter getDoctorMyPresenter();
}
