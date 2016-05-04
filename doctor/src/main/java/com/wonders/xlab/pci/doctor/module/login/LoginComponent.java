package com.wonders.xlab.pci.doctor.module.login;

import com.wonders.xlab.pci.doctor.di.ApplicationComponent;
import com.wonders.xlab.pci.doctor.di.scope.ActivityScoped;
import com.wonders.xlab.pci.doctor.mvp.presenter.LoginPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = LoginModule.class)
public interface LoginComponent {
    LoginPresenter getLoginPresenter();
}
