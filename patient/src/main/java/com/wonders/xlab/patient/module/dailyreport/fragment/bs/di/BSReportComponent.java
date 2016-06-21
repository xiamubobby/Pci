package com.wonders.xlab.patient.module.dailyreport.fragment.bs.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.bs.BSReportCachePresenter;

import dagger.Component;

/**
 * Created by WZH on 16/6/21.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = BSReportModule.class)
public interface BSReportComponent {
    BSReportCachePresenter getBSReportCachePresenter();
}
