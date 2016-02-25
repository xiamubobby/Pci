package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface LoginAPI {

    @POST("login")
    Observable<LoginEntity> login();
}
