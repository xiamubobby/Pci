package com.wonders.xlab.patient.module.dailyreport.fragment.symptom.di;

import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.SymptomReportContract;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by WZH on 16/6/20.
 */
@Module
public class SymptomReportModule {

    private SymptomReportContract.ViewListener mViewListener;

    public SymptomReportModule(SymptomReportContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    SymptomReportContract.ViewListener provideSymptomReportViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    SymptomAPI provideSymptomAPI(Retrofit retrofit) {
        return retrofit.create(SymptomAPI.class);
    }
}
