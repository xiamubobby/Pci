package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.data.api.LoginAPI;

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
public class ApiModule {

    @Provides
    @Singleton
    protected Retrofit provideRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        OkHttpClient client = builder.build();

        return new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    protected LoginAPI provideLoginAPI(Retrofit retrofit) {
        return retrofit.create(LoginAPI.class);
    }
}
