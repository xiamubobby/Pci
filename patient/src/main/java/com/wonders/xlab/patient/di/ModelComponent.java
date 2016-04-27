package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {ModelModule.class,ApiModule.class})
public interface ModelComponent {
    LoginModel getLoginModel();

    DoctorAllModel getDoctorAllModel();
}