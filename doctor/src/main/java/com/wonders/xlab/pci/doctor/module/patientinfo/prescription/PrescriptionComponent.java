package com.wonders.xlab.pci.doctor.module.patientinfo.prescription;

import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.presenter.PrescriptionPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/5.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = PrescriptionModule.class)
public interface PrescriptionComponent {

    PrescriptionPresenter getPrescriptionPresenter();
}
