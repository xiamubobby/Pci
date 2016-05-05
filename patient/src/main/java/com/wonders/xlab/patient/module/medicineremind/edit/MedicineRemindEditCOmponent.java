package com.wonders.xlab.patient.module.medicineremind.edit;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;

import dagger.Component;

/**
 * Created by WZH on 16/5/5.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = MedicineRemindEditModule.class)
public interface MedicineRemindEditComponent {

}
