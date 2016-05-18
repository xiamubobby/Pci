package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    Retrofit provideRetrofit() {
        String endPoint = Constant.BASE_URL;//BuildConfig.DEBUG ? Constant.BASE_URL_DEBUG : Constant.BASE_URL;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        /*if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }*/

        return new Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//必须加上
                .client(builder.build())
                .build();
    }

    @Provides
    @Singleton
    AIManager provideAIManager() {
        return new AIManager();
    }

    @Provides
    @Singleton
    Realm provideRealm(XApplication application) {
        RealmConfiguration config = new RealmConfiguration.Builder(application).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }
}
