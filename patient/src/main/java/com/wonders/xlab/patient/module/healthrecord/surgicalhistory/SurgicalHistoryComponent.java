package com.wonders.xlab.patient.module.healthrecord.surgicalhistory;


import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.SurgicalHistoryPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = SurgicalHistoryModule.class)
public interface SurgicalHistoryComponent {
    SurgicalHistoryPresenter getSurgicalHistoryPresenter();
}
