package com.wonders.xlab.patient.data.api;


import com.wonders.xlab.patient.data.entity.LoginEntity;

import java.util.HashMap;

import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/13.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("user/login")
    Observable<Response<LoginEntity>> login(@FieldMap HashMap<String, String> body);
}