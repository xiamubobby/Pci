package com.wonders.xlab.patient.module.order;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.OrderListApi;
import com.wonders.xlab.patient.mvp.presenter.OrderListPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by jimmy on 16/5/9.
 */
@Module
public class OrderListModule {

    private OrderListPresenterContract.viewListener mViewListener;

    public OrderListModule(OrderListPresenterContract.viewListener viewListener) {
        this.mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    public OrderListPresenterContract.viewListener provideOrderListViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    public OrderListApi provideOrderListApi(Retrofit retrofit) {
        return retrofit.create(OrderListApi.class);
    }
}
