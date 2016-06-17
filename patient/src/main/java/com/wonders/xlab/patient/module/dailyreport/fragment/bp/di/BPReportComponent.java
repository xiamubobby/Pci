package com.wonders.xlab.patient.module.dailyreport.fragment.bp.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.bp.BPReportCachePresenter;

import dagger.Component;

/**
 * Created by WZH on 16/6/17.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class,modules = BPReportModule.class)
public interface BPReportComponent {
    BPReportCachePresenter getBPReportCachePresenter();
}
