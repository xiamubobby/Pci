package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.RegisterEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/5/4.
 */
public interface AuthAPI {
    @GET("v1/users/sendMessage/{mobile}")
    Observable<Response<SimpleEntity>> getCapture(@Path("mobile") String mobile);

    @POST("v1/users/register")
    Observable<Response<RegisterEntity>> register(@Field("tel") String tel,@Field("password") String password,@Field("capture") String capture);
}
