package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/2/23.
 */
public interface UserInfoAPI {
    @GET("getUserInfo")
    Observable<UserInfoEntity> getUserInfo();
}
