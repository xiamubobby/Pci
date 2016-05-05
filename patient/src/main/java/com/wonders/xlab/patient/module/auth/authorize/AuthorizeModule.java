package com.wonders.xlab.patient.module.auth.authorize;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.AuthAPI;
import com.wonders.xlab.patient.mvp.presenter.AuthorizePresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class AuthorizeModule {
    private AuthorizePresenterContract.ViewListener mViewListener;

    public AuthorizeModule(AuthorizePresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    AuthorizePresenterContract.ViewListener provideAuthorizePresenterViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    AuthAPI provideAuthAPI(Retrofit retrofit) {
        return retrofit.create(AuthAPI.class);
    }
}
