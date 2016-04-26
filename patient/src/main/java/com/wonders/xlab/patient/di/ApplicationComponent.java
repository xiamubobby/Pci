package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.application.XApplication;

import dagger.Component;

/**
 * Created by hua on 16/4/25.
 */
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

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
