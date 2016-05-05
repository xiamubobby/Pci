package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory;

import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.presenter.SurgicalHistoryPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class,modules = SurgicalHistoryModule.class)
public interface SurgicalHistoryComponent {
    SurgicalHistoryPresenter getSurgicalHistoryPresenter();
}
