package com.wonders.xlab.patient.module.me.doctor.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.model.DoctorListModel;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenter;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by xiamubobby on 16/7/6.
 */

@Module
public class DoctorListModule {
    private DoctorListPresenterContract.ViewListener listener;

    public DoctorListModule(DoctorListPresenterContract.ViewListener listener) {
        this.listener = listener;
    }

    @Provides
    @ActivityScoped
    DoctorListPresenterContract.ViewListener provideDoctorListViewListener() {
        return listener;
    }

    @Provides
    @ActivityScoped
    DoctorAPI provideDoctorApi(Retrofit retrofit) {
        return retrofit.create(DoctorAPI.class);
    }
}
