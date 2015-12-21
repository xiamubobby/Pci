package com.wonders.xlab.pci.mvn.api.task;

import com.wonders.xlab.pci.mvn.entity.SimpleEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface AddRecordAPI {
    @FormUrlEncoded
    @POST("userBloodSugar/saveUserBloodSugar")
    Observable<SimpleEntity> saveBS(@Field("userId") String userId, @Field("date") long date, @Field("timeIndex") int timeIndex, @Field("bloodSugarValue") double bloodSugarValue);

    @FormUrlEncoded
    @POST("userBloodPressure/saveUserPressure")
    Observable<SimpleEntity> saveBP(@Field("userId") String userId, @Field("date") long date, @Field("heartRate") int heartRate, @Field("systolicPressure") int systolicPressure, @Field("diastolicPressure") int diastolicPressure);

    @FormUrlEncoded
    @POST("smoke/saveUserSmoke")
    Observable<SimpleEntity> saveCigarette(@Field("userId") String userId, @Field("date") long date, @Field("cigaretteCount") int cigaretteCount);

    @FormUrlEncoded
    @POST("wine/saveUserWine")
    Observable<SimpleEntity> saveWine(@Field("userId") String userId, @Field("date") long date, @Field("wineCount") int wineCount, @Field("wineIndex") int wineIndex);

    @FormUrlEncoded
    @POST("userSymptom/saveUserSymptom")
    Observable<SimpleEntity> saveSymptom(@Field("userId") String userId, @Field("symptomIdsStr") String[] symptomIdsStr);


}
