package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.UserInfoEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/23.
 */
public interface UserInfoAPI {
    @GET("userInfos/retrieveUserInfo/{userId}")
    Observable<UserInfoEntity> getUserInfo(@Path("userId") String userId);
}
