package com.wonders.xlab.pci.doctor.module.patientlist;

import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by hua on 16/5/19.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = PatientModule.class)
public interface PatientComponent {
    PatientPresenter getPatientPresenter();
}
