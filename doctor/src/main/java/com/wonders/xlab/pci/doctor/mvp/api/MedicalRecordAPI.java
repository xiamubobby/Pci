package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("reviews/listReviews/{userId}")
    Observable<MedicalRecordEntity> getMedicalRecordList(@Path("userId") String userId);
}
