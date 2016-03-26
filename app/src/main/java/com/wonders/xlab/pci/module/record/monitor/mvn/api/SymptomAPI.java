package com.wonders.xlab.pci.module.record.monitor.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.SymptomEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/22.
 */
public interface SymptomAPI {

    @FormUrlEncoded
    @POST("userSymptom/retrieveUserSymptom")
    Observable<SymptomEntity> getSymptoms(@Field("userId") String userId, @Field("date") long date);
}
