package com.wonders.xlab.patient.module.dailyreport.fragment.bp.di;

import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.module.dailyreport.fragment.bp.BPReportContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WZH on 16/6/17.
 */
@Module
public class BPReportModule {
    private BPReportContract.ViewListener mViewListener;

    public BPReportModule(BPReportContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    BPReportContract.ViewListener provideBPReportViewListener() {
        return mViewListener;
    }
}
