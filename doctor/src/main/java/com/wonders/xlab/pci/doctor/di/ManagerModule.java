package com.wonders.xlab.pci.doctor.di;


import com.wonders.xlab.pci.doctor.BuildConfig;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.application.AIManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import im.hua.library.base.retrofit.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class ManagerModule {

    /**
     * the scope of the providers must the same with its component's scope
     * @return
     */
    @Provides
    @Singleton
    protected Retrofit provideRetrofit() {
        String endPoint = BuildConfig.DEBUG ? Constant.BASE_URL_DEBUG : Constant.BASE_URL;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        OkHttpClient client = builder.build();

        return new Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    protected AIManager provideAIManager() {
        return new AIManager();
    }
}
