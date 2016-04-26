package com.wonders.xlab.patient.data.api;


import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/1/6.
 */
public interface IdealRangeAPI {
    @GET("userBloodPressureStandard/getBloodPressureStandard/{userId}")
    Observable<Response<SimpleEntity>> fetchIdealBPRange(@Path("userId") String userId);

    @GET("userBloodSugarStandard/listBloodSugarStandard/{userId}")
    Observable<Response<SimpleEntity>> fetchIdealBSRange(@Path("userId") String userId);
}
