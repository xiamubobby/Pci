package com.wonders.xlab.patient.module.home;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by hua on 16/5/18.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = HomeModule.class)
public interface HomeComponent {
    HomePresenter getHomePresenter();
}
