package com.wonders.xlab.patient.module.healthrecord.medicalrecords;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.MedicalRecordsPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/11.
 */

@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = MedicalRecordsModule.class)
public interface MedicalRecordsComponent {

    MedicalRecordsPresenter getMedicalRecordsPresenter();
}
