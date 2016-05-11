package com.wonders.xlab.patient.module.me.userinfo;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;
import com.wonders.xlab.patient.mvp.presenter.UserInfoPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/11.
 */
@Module
public class UserInfoModule {
    private UserInfoPresenterContract.ViewListener mViewListener;

    public UserInfoModule(UserInfoPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    UserInfoPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    UserInfoAPI provideUserInfoAPI(Retrofit retrofit) {
        return retrofit.create(UserInfoAPI.class);
    }
}
