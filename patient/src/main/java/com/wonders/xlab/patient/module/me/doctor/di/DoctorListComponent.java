package com.wonders.xlab.patient.module.me.doctor.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.address.AddressPresenter;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenter;

import dagger.Component;

/**
 * Created by xiamubobby on 16/7/6.
 */

@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = DoctorListModule.class)
public interface DoctorListComponent {
    DoctorListPresenter getDoctorListPresenter();
}
