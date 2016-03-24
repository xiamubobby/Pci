package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.mvp.entity.BSSaveEntity;

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
public interface BSAPI {
    @FormUrlEncoded
    @POST("v1/bloodSugars/1/saveUserBloodSugar")
    Observable<BSSaveEntity> saveBSSingle(@Field("userId") String userId, @Field("date") long date, @Field("timeIndex") int timeIndex, @Field("bloodSugarValue") float bloodSugarValue);

    @POST("v1/bloodSugars/saveUserBloodSugarByJson/{userId}")
    Observable<SimpleEntity> saveBS(@Path("userId") String userId, @Body BSEntityList bodyList);

}
