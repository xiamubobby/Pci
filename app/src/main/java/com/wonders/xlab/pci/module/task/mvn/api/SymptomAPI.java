package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.task.SymptomEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    @GET("symptom/retrieveSymptom")
    Observable<SymptomEntity> getSymptoms();
}
