package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.entity.LoginEntity;

import java.util.HashMap;

import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 15/12/13.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginEntity> login(@FieldMap HashMap<String,String> body);
}
