package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.presenter.IDoctorAllPresenter;
import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorAllPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class PresenterModule {
    /**
     * 登录
     * @return
     */
    private LoginPresenter.LoginPresenterListener mLoginPresenterListener;

    public PresenterModule(LoginPresenter.LoginPresenterListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;
    }

    @Provides
    protected LoginPresenter.LoginPresenterListener provideLoginPresenterListener() {
        return mLoginPresenterListener;
    }

    @Provides
    protected ILoginPresenter provideLoginPresenter(LoginPresenter.LoginPresenterListener loginPresenterListener) {
        return new LoginPresenter(loginPresenterListener);
    }

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
    protected IDoctorAllPresenter provideDoctorAllPresenter(DoctorAllPresenter.DoctorAllPresenterListener listener) {
        return new DoctorAllPresenter(listener);
    }
}
