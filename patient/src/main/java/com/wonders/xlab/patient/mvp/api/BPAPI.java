package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.mvp.entity.BPSaveEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/3/24.
 */
public interface BPAPI {
    @FormUrlEncoded
    @POST("v1/bloodPressures/1/saveUserPressure")
    Observable<BPSaveEntity> saveBPSingle(@Field("userId") String userId, @Field("date") long date, @Field("heartRate") int heartRate, @Field("systolicPressure") int systolicPressure, @Field("diastolicPressure") int diastolicPressure);

    @POST("v1/bloodPressures/saveUserPressureByJson/{userId}")
    Observable<SimpleEntity> saveBP(@Path("userId") String userId, @Body BPEntityList bodyList);

}
