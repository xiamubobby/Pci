package com.wonders.xlab.patient.module.me.userinfo;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/11.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = UserInfoModule.class)
public interface UserInfoComponent {
    UserInfoPresenter getUserInfoPresenter();
}