package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.MedicalRecordEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("http://xlab-tech.com:45675/pci-doctor/v1/reviews/listReviews/{userId}")
    Observable<MedicalRecordEntity> getMedicalRecordList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);
}
