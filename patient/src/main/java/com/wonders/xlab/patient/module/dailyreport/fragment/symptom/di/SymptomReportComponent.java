package com.wonders.xlab.patient.module.dailyreport.fragment.symptom.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.SymptomReportPresenter;

import dagger.Component;

/**
 * Created by WZH on 16/6/20.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = SymptomReportModule.class)
public interface SymptomReportComponent {
        SymptomReportPresenter getSymptomReportPresenter();
}
