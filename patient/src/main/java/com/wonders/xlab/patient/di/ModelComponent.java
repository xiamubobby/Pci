package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {ModelModule.class,ManagerModule.class})
public interface ModelComponent {

    DoctorAllModel getDoctorAllModel();
}