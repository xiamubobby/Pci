package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("reviews/listReviews/{userId}")
    Observable<MedicalRecordEntity> getMedicalRecordList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);
}
