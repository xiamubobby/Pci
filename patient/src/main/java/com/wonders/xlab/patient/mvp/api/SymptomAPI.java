package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    /**
     * 获取不适症状字典表
     * @return
     */
    @GET("symptom/retrieveSymptom")
    Observable<SymptomEntity> getSymptomDicList();

    /**
     * 获取
     * @param userId
     * @return
     */
    @GET("userSymptomRecords/listUserSymptomRecord/{userId}")
    Observable<SymptomEntity> getSymptomList(@Path("userId") String userId);

}
