package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.di.ApiModule;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {LoginModelModule.class, ApiModule.class})
public interface LoginModelComponent {
    LoginModel getLoginModel();
}