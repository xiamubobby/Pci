package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.di.LoginPresenterModule;
import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Component(modules = {LoginPresenterModule.class})
public interface LoginComponent {
    ILoginPresenter getILoginPresenter();
}