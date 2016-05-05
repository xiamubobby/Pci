package com.wonders.xlab.pci.doctor.module.patientinfo.indicator;

import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.presenter.TestIndicatorPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/5.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = TestIndicatorModule.class)
public interface TestIndicatorComponent {
    TestIndicatorPresenter getTestIndicatorPresenter();
}
