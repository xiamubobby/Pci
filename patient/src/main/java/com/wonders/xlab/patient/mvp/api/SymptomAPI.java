package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    @GET("symptom/retrieveSymptom")
    Observable<SymptomEntity> getSymptoms();
}
