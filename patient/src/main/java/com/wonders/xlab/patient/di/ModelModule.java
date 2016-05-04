package com.wonders.xlab.patient.di;

import com.wonders.xlab.patient.mvp.model.impl.DoctorAllModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class ModelModule {

    private DoctorAllModel.DoctorAllModelListener mDoctorAllModelListener;

    public ModelModule(DoctorAllModel.DoctorAllModelListener doctorAllModelListener) {
        mDoctorAllModelListener = doctorAllModelListener;
    }

    @Provides
    protected DoctorAllModel.DoctorAllModelListener provideDoctorAllModelListener(){
        return mDoctorAllModelListener;
    }
}
