package com.wonders.xlab.patient.data.api;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.data.entity.BPSaveEntity;
import com.wonders.xlab.patient.data.entity.BloodPressureEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/3/24.
 */
public interface BPAPI {
    @FormUrlEncoded
    @POST("v1/bloodPressures/1/saveUserPressure")
    Observable<Response<BPSaveEntity>> saveBPSingle(@Field("userId") String userId, @Field("date") long date, @Field("heartRate") int heartRate, @Field("systolicPressure") int systolicPressure, @Field("diastolicPressure") int diastolicPressure);

    @POST("v1/bloodPressures/saveUserPressureByJson/{userId}")
    Observable<Response<SimpleEntity>> saveBP(@Path("userId") String userId, @Body BPEntityList bodyList);

    @GET("v1/bloodPressures/listUserPressures/{userId}")
    Observable<Response<BloodPressureEntity>> getBPList(@Path("userId") String patientId, @Query("start") long startTime, @Query("end") long endTime,@Query("page") int page,@Query("size") int size);

}
