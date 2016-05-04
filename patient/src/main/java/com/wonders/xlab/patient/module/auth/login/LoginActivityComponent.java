package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.di.ManagerComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.LoginPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/4.
 */
@ActivityScoped
@Component(dependencies = ManagerComponent.class,modules = LoginModule.class)
public interface LoginActivityComponent {
    LoginPresenter getLoginPresenter();
}
