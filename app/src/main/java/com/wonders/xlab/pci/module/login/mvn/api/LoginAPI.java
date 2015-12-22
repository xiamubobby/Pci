package com.wonders.xlab.pci.module.login.mvn.api;

import com.wonders.xlab.pci.module.login.mvn.entity.LoginEntity;

import java.util.HashMap;

import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 15/12/13.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("user/login/{id}")
    Observable<LoginEntity> login(@FieldMap HashMap<String,String> body);
}
