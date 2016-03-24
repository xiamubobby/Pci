package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    /**
     * 获取不适症状字典表
     *
     * @return
     */
    @GET("symptom/retrieveSymptom")
    Observable<SymptomEntity> getSymptomDicList();

    /**
     * 获取不适症状（每日记录界面）
     *
     * @param userId
     * @param startTimeInMill
     * @param endTimeInMill
     * @return
     */
    @GET("v1/symptoms/listUserSymptoms")
    Observable<SymptomRetrieveEntity> getSymptomList(@Query("userId") String userId, @Query("start") long startTimeInMill, @Query("end") long endTimeInMill);

    @FormUrlEncoded
    @POST("v1/symptoms/saveUserSymptom")
    Observable<SimpleEntity> saveSymptom(@Field("userId") String userId, @Field("symptomIdsStr") String[] symptomIdsStr);

}
