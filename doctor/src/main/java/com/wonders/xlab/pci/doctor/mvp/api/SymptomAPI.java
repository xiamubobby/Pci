package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomAPI {

    @GET("getSymptomList")
    Observable<SymptomEntity> getSymptomList();
}
