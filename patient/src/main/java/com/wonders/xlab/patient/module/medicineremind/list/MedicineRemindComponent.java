package com.wonders.xlab.patient.module.medicineremind.list;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = MedicineRemindModule.class)
public interface MedicineRemindComponent {
    MedicineRemindPresenter getMedicineRemindPresenter();
}
