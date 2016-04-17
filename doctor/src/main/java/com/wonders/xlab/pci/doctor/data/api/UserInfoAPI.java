package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.UserInfoEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/23.
 */
public interface UserInfoAPI {
    @GET("v1/userInfos/retrieveUserInfo/{userId}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userId") String userId);
}
