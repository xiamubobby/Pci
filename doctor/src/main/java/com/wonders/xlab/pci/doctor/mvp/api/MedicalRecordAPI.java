package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicalRecordAPI {
    @GET("getMedicalRecordList")
    Observable<MedicalRecordEntity> getMedicalRecordList();
}
