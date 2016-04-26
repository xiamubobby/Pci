package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Component(modules = {LoginPresenterModule.class})
public interface LoginPresenterComponent {
    ILoginPresenter getILoginPresenter();
}