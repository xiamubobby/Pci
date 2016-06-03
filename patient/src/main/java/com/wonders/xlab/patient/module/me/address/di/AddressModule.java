package com.wonders.xlab.patient.module.me.address.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.address.AddressContract;
import com.wonders.xlab.patient.mvp.api.UserInfoAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class AddressModule {
    private AddressContract.ViewListener mViewListener;

    public AddressModule(AddressContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    AddressContract.ViewListener provideAddressViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    UserInfoAPI provideAddressAPI(Retrofit retrofit) {
        return retrofit.create(UserInfoAPI.class);
    }
}
