package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.presenter.impl.DoctorAllPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class PresenterModule {

    /**
     * 所有医生
     * @return
     */
    private DoctorAllPresenter.DoctorAllPresenterListener mDoctorAllPresenterListener;

    public PresenterModule(DoctorAllPresenter.DoctorAllPresenterListener doctorAllPresenterListener) {
        mDoctorAllPresenterListener = doctorAllPresenterListener;
    }

    @Provides
    protected DoctorAllPresenter.DoctorAllPresenterListener provideDoctorAllPresenterListener() {
        return mDoctorAllPresenterListener;
    }

    @Provides
    protected DoctorAllPresenter provideDoctorAllPresenter(DoctorAllPresenter.DoctorAllPresenterListener listener) {
        return new DoctorAllPresenter(listener);
    }
}