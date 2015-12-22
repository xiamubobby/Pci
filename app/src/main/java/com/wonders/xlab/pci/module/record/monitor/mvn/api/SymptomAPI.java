package com.wonders.xlab.pci.module.record.monitor.mvn.api;

import com.wonders.xlab.pci.module.record.monitor.mvn.entity.SymptomEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/22.
 */
public interface SymptomAPI {

    @FormUrlEncoded
    @POST("userSymptom/retrieveUserSymptom")
    Observable<SymptomEntity> getSymptoms(@Field("userId") String userId, @Field("date") long date);
}
