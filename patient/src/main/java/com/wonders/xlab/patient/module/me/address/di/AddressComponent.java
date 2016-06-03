package com.wonders.xlab.patient.module.me.address.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.address.AddressPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = AddressModule.class)
public interface AddressComponent {
    AddressPresenter getAddressPresenter();
}
