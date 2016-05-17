package com.wonders.xlab.patient.module.home.top;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by hua on 16/5/17.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = HomeTopModule.class)
public interface HomeTopComponent {
    HomeTopPresenter getHomeTopPresenter();
}