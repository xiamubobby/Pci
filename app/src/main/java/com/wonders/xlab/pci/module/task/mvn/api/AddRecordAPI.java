package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
