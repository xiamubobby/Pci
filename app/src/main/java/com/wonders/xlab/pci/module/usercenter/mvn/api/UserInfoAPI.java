package com.wonders.xlab.pci.module.usercenter.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.userinfo.UserInfoEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 15/12/17.
 */
public interface UserInfoAPI {
    @GET("userInfo/retrieveUserInfo/{userId}")
    Observable<UserInfoEntity> getUserInfo(@Path("userId") String userId);
}
