package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.assist.connection.entity.BPEntityList;
import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;

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
    @POST("userBloodSugar/saveUserBloodSugar")
    Observable<SimpleEntity> saveBS(@Field("userId") String userId, @Field("date") long date, @Field("timeIndex") int timeIndex, @Field("bloodSugarValue") float bloodSugarValue);

    @FormUrlEncoded
    @POST("userBloodPressure/saveUserPressure")
    Observable<SimpleEntity> saveBPSingle(@Field("userId") String userId, @Field("date") long date, @Field("heartRate") int heartRate, @Field("systolicPressure") int systolicPressure, @Field("diastolicPressure") int diastolicPressure);

    @POST("userBloodPressure/saveUserPressureByJson/{userId}")
    Observable<SimpleEntity> saveBP(@Path("userId") String userId, @Body BPEntityList bodyList);

    @FormUrlEncoded
    @POST("userSymptom/saveUserSymptom")
    Observable<SimpleEntity> saveSymptom(@Field("userId") String userId, @Field("symptomIdsStr") String[] symptomIdsStr);


}
