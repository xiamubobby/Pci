package com.wonders.xlab.patient.mvp.api;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/5/4.
 */
public interface AuthAPI {
    @GET("v1/users/sendMessage/{mobile}")
    Observable<Response<SimpleEntity>> getCapture(@Path("mobile") String mobile);
}
