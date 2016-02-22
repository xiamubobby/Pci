package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/19.
 */
public interface PatientAPI {
    @GET("getPatientList")
    Observable<PatientEntity> getPatientList();
}
