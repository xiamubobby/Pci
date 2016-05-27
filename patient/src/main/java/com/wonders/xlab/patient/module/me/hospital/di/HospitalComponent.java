package com.wonders.xlab.patient.module.me.hospital.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.hospital.HospitalPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/27.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = HospitalModule.class)
public interface HospitalComponent {
    HospitalPresenter getHospitalPresenter();
}
