package com.wonders.xlab.pci.mvn.api;

import com.wonders.xlab.pci.mvn.BaseEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 15/12/13.
 */
public interface LoginAPI {

    @GET("users/memberInfo/99")
    Observable<BaseEntity> login();
}
