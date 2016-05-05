package com.wonders.xlab.patient.module.auth.register;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.RegisterPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = RegisterModule.class)
public interface RegisterComponent {
    RegisterPresenter getRegisterPresenter();
}
