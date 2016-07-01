package com.wonders.xlab.patient.module.auth.login.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.auth.login.LoginPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/4.
 * 如果是Activity则设为：@ActivityScoped
 * 如果是Fragment则设为：@FragmentScoped
 *
 * Scope必须与ApplicationComponent的Scope不一样
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = LoginModule.class)
public interface LoginComponent {
    /**
     * 暴露Presenter接口
     * @return
     */
    LoginPresenter getLoginPresenter();
}
