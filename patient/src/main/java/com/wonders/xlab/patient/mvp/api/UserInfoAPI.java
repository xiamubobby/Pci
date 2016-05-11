package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;
import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/5/10.
 */
public interface UserInfoAPI {
    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    @GET("v1/users/getUserInfo/{userId}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userId") String userId);

    /**
     * 修改用户基本信息
     * @param body
     * @return
     */
    @POST("v1/users/updateUser/{userId}")
    Observable<Response<EmptyValueEntity>> modifyUserInfo(@Body UserInfoBody body,@Path("userId") String userId);

    /**
     * 获取医院字典表
     * @return
     */
    @GET("hospitals")
    Observable<Response<HospitalAllEntity>> getHospitals();
}
