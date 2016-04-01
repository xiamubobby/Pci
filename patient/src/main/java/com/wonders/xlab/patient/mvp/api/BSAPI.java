package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.mvp.entity.BSSaveEntity;
import com.wonders.xlab.patient.mvp.entity.BloodSugarEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface BSAPI {
    @FormUrlEncoded
    @POST("v1/bloodSugars/1/saveUserBloodSugar")
    Observable<Response<BSSaveEntity>> saveBSSingle(@Field("userId") String userId, @Field("date") long date, @Field("timeIndex") int timeIndex, @Field("bloodSugarValue") float bloodSugarValue);

    @POST("v1/bloodSugars/saveUserBloodSugarByJson/{userId}")
    Observable<Response<SimpleEntity>> saveBS(@Path("userId") String userId, @Body BSEntityList bodyList);

    @GET("v1/bloodSugars/listBloodSugarRecord/{userId}")
    Observable<Response<BloodSugarEntity>> getBSList(@Path("userId") String userId);
}
