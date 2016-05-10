package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.TestIndicatorEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jimmy on 16/5/5.
 */
public interface TestIndicatorAPI {

    @GET("http://172.16.74.6:8080/pci-doctor/v1/healthRecords/physicalExaminationRecord/{patientId}/{pageIndex}")
    Observable<Response<TestIndicatorEntity>> getTestIndicatorList(@Path("patientId") String patientId, @Path("pageIndex") int pageIndex);
}
