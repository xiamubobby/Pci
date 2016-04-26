package com.wonders.xlab.patient.data.api;


import com.wonders.xlab.patient.data.entity.MedicalRecordEntity;

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
}
