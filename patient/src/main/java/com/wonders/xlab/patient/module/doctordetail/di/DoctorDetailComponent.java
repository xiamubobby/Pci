package com.wonders.xlab.patient.module.doctordetail.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.doctordetail.DoctorDetailOrGroupDetailPresenter;

import dagger.Component;

/**
 * Created by WZH on 16/6/22.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = DoctorDetailModule.class)
public interface DoctorDetailComponent {
    DoctorDetailOrGroupDetailPresenter getDoctorDetailOrGroupDetailPresenter();
}
