package com.wonders.xlab.patient.module.medicineremind.searchmedicine.di;

import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.MedicineSearchPresenter;

import dagger.Component;

/**
 * Created by hua on 16/5/6.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = MedicineSearchModule.class)
public interface MedicineSearchComponent {
    MedicineSearchPresenter getMedicineSearchPresenter();
}
