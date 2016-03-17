package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface AddRecordAPI {
    @FormUrlEncoded
    @POST("v1/bloodSugars/saveUserBloodSugar")
    Observable<SimpleEntity> saveBSSingle(@Field("userId") String userId, @Field("date") long date, @Field("timeIndex") int timeIndex, @Field("bloodSugarValue") float bloodSugarValue);

    @POST("v1/bloodSugars/saveUserBloodSugarByJson/{userId}")
    Observable<SimpleEntity> saveBS(@Path("userId") String userId, @Body BSEntityList bodyList);

    @FormUrlEncoded
    @POST("v1/bloodPressures/saveUserPressure")
    Observable<SimpleEntity> saveBPSingle(@Field("userId") String userId, @Field("date") long date, @Field("heartRate") int heartRate, @Field("systolicPressure") int systolicPressure, @Field("diastolicPressure") int diastolicPressure);

    @POST("v1/bloodPressures/saveUserPressureByJson/{userId}")
    Observable<SimpleEntity> saveBP(@Path("userId") String userId, @Body BPEntityList bodyList);

    @FormUrlEncoded
    @POST("v1/symptoms/saveUserSymptom")
    Observable<SimpleEntity> saveSymptom(@Field("userId") String userId, @Field("symptomIdsStr") String[] symptomIdsStr);
}
