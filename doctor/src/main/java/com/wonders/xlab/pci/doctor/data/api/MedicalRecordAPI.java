package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.data.entity.MedicalRecordsEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("v1/reviews/listReviews/{userId}")
    Observable<Response<MedicalRecordEntity>> getMedicalRecordList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     *
     * @param patientId
     * @param pageIndex 分页页码
     * @return
     */
    @GET("v1/healthRecords/retrieveUserTreatmentInfo/{patientId}/{pageIndex}")
    Observable<Response<MedicalRecordsEntity>> getMedicalRecordsList(@Path("patientId") String patientId,@Path("pageIndex") int pageIndex);
}
