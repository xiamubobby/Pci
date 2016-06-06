package com.wonders.xlab.patient.module.auth.authorize.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.auth.authorize.AuthorizePresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = AuthorizeModule.class)
public interface AuthorizeComponent {
    AuthorizePresenter getAuthorizePresenter();
}
