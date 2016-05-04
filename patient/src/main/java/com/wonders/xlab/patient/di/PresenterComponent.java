package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.presenter.impl.DoctorAllPresenter;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {

    DoctorAllPresenter getDoctorAllPresenter();
}