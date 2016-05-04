package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("v1/doctors/login")
    Observable<Response<LoginEntity>> login(@Field("tel") String tel, @Field("password") String password);
}
