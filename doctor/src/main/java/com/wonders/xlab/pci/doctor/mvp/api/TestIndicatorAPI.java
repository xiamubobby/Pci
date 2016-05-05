package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.TestIndicatorEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jimmy on 16/5/5.
 */
public interface TestIndicatorAPI {

    @GET("v1/healthRecords/physicalExaminationRecord/{patientId}/{pageIndex}")
    Observable<Response<TestIndicatorEntity>> getTestIndicatorList(@Path("patientId") String patientId, @Path("pageIndex") int pageIndex);
}
