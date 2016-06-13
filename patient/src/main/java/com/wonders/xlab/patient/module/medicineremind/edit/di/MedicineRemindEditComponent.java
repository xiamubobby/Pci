package com.wonders.xlab.patient.module.medicineremind.edit.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.medicineremind.edit.MedicineRemindEditCachePresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/6.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = MedicineRemindEditModule.class)
public interface MedicineRemindEditComponent {
    MedicineRemindEditCachePresenter getMedicineRemindEditPresenter();
}