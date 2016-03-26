package com.wonders.xlab.pci.module.login.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.login.LoginEntity;

import java.util.HashMap;

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
    Observable<LoginEntity> login(@FieldMap HashMap<String,String> body);
}