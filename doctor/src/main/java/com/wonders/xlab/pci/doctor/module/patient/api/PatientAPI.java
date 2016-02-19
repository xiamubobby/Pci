package com.wonders.xlab.pci.doctor.module.patient.api;

import com.wonders.xlab.pci.doctor.module.networkentity.PatientEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/19.
 */
public interface PatientAPI {
    @GET("")
    Observable<PatientEntity> getPatientList();
}
