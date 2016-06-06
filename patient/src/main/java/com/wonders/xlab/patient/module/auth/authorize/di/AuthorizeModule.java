package com.wonders.xlab.patient.module.auth.authorize.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.auth.authorize.AuthorizeContract;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class AuthorizeModule {
    private AuthorizeContract.ViewListener mViewListener;

    public AuthorizeModule(AuthorizeContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    AuthorizeContract.ViewListener provideAuthorizePresenterViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    AuthAPI provideAuthAPI(Retrofit retrofit) {
        return retrofit.create(AuthAPI.class);
    }
}
