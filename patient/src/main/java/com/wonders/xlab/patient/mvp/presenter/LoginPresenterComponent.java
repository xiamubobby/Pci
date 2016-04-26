package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.di.ApiModule;
import com.wonders.xlab.patient.di.LoginModelModule;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {LoginModelModule.class, ApiModule.class})
public interface LoginPresenterComponent {
    LoginModel getLoginModel();
}
