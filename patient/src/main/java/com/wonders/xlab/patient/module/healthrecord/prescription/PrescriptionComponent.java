package com.wonders.xlab.patient.module.healthrecord.prescription;


import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.PrescriptionPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = PrescriptionModule.class)
public interface PrescriptionComponent {

    PrescriptionPresenter getPrescriptionPresenter();
}
