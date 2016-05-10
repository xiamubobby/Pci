package com.wonders.xlab.patient.module.healthrecord.testindicator;


import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.TestIndicatorPresenter;

import dagger.Component;

/**
 * Created by jimmy on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = TestIndicatorModule.class)
public interface TestIndicatorComponent {
    TestIndicatorPresenter getTestIndicatorPresenter();
}
