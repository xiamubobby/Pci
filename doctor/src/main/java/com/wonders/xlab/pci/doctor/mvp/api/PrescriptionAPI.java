package com.wonders.xlab.pci.doctor.mvp.api;


import com.wonders.xlab.pci.doctor.mvp.entity.PrescriptionEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jimmy on 16/5/5.
 */
public interface PrescriptionAPI {
    @GET(" v1/healthRecords/pharmacyRecordList/{patientId}/{pageIndex}")
    Observable<Response<PrescriptionEntity>> getPrescriptionList(@Path("patientId") String patientId, @Path("pageIndex") int pageIndex);
}
