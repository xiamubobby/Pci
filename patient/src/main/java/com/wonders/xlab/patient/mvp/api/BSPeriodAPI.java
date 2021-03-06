package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.BSPeriodEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/3/21.
 */
public interface BSPeriodAPI {
    @GET("v1/bloodSugars/listBloodSugarTimes/{currentTime}")
    Observable<Response<BSPeriodEntity>> getPeriodDic(@Path("currentTime") long currentTime);
}
