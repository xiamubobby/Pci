package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/19.
 */
public interface PatientAPI {
    @GET("v1/userInfos/listUserInfos/{doctorId}")
    Observable<Response<PatientEntity>> getPatientList(@Path("doctorId") String doctorId);
}
