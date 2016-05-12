package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.util.AlarmUtil;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class})
public interface ApplicationComponent {

    void inject(Object object);

    XApplication getXApplication();

    //TODO Important ,figure it out later
    // downstream components need these exposed
    Retrofit getRetrofit();

    AIManager getAIManager();

    Realm getRealm();

    AlarmUtil getAlarmUtil();

    final class Initializer {
        private Initializer() {
        }

        public static ApplicationComponent init(XApplication application) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
