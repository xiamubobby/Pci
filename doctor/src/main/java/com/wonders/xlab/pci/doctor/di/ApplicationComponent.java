package com.wonders.xlab.pci.doctor.di;


import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.application.XApplication;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/4/25.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class})
public interface ApplicationComponent {

    void inject(Object object);

    //TODO Important ,figure it out later
    // downstream components need these exposed
    Retrofit getRetrofit();

    AIManager getAIManager();

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
