package com.wonders.xlab.patient.module.me.userinfo.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.userinfo.UserInfoContract;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/11.
 */
@Module
public class UserInfoModule {
    private UserInfoContract.ViewListener mViewListener;

    public UserInfoModule(UserInfoContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    UserInfoContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    UserInfoAPI provideUserInfoAPI(Retrofit retrofit) {
        return retrofit.create(UserInfoAPI.class);
    }
}
