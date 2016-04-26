package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class LoginModelModule {

    private LoginModel.LoginModelListener mLoginModelListener;

    private DoctorAllModel.DoctorAllModelListener mDoctorAllModelListener;

    public LoginModelModule(LoginModel.LoginModelListener loginModelListener) {
        mLoginModelListener = loginModelListener;
    }

    public LoginModelModule(DoctorAllModel.DoctorAllModelListener doctorAllModelListener) {
        mDoctorAllModelListener = doctorAllModelListener;
    }

    @Provides
    protected LoginModel.LoginModelListener provideLoginModelListener() {
        return mLoginModelListener;
    }

    @Provides
    protected DoctorAllModel.DoctorAllModelListener provideDoctorAllModelListener(){
        return mDoctorAllModelListener;
    }
}
