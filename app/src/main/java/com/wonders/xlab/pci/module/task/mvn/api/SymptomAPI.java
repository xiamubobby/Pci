package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.mvn.entity.task.SymptomEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    @GET("symptom/retrieveSymptom")
    Observable<SymptomEntity> getSymptoms();

    Observable<SimpleEntity> saveSymptoms();
}
