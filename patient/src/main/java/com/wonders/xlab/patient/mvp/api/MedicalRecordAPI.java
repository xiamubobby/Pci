package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.patient.mvp.entity.MedicalRecordsEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("userCaseReview/listUserCaseReviewRecords/{userId}")
    Observable<Response<MedicalRecordEntity>> getMedicalRecordList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * @param patientId
     * @param page 分页页码
     * @return
     */
    @GET("v1/healthRecords/retrieveUserTreatmentInfo/{patientId}/{page}")
    Observable<Response<MedicalRecordsEntity>> getMedicalRecordsList(@Path("patientId") String patientId, @Path("page") int page, @Query("size") int size);
}
