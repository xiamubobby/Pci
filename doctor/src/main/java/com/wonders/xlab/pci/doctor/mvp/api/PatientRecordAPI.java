package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.SurgicalHistoryEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/5/5.
 */
public interface PatientRecordAPI {

    @GET("v1/healthRecords/hospitalizedRecord/{patientId}/{pageIndex}")
    Observable<Response<SurgicalHistoryEntity>> getSurgicalHistory(@Path("patientId") String patientId,@Path("pageIndex") int pageIndex);
}
