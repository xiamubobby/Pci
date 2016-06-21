package com.wonders.xlab.patient.module.dailyreport.fragment.bs.di;

import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.bs.BSReportContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WZH on 16/6/21.
 */
@Module
public class BSReportModule {
    private BSReportContract.ViewListener mViewListener;

    public BSReportModule(BSReportContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    BSReportContract.ViewListener provideBSReportViewListener() {
        return mViewListener;
    }
}
