package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.UserInfoEntity;
import com.wonders.xlab.patient.mvp.entity.request.UserInfoBody;

import im.hua.library.base.mvp.entity.EmptyValueEntity;
import im.hua.library.base.mvp.entity.SimpleEntity;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/5/10.
 */
public interface UserInfoAPI {
    /**
     * 获取用户基本信息
     *
     * @param userId
     * @return
     */
    @GET("v1/users/getUserInfo/{userId}")
    Observable<Response<UserInfoEntity>> getUserInfo(@Path("userId") String userId);

    /**
     * 修改用户基本信息
     *
     * @param body
     * @return
     */
    @POST("v1/users/updateUser/{userId}")
    Observable<Response<EmptyValueEntity>> modifyUserInfo(@Body UserInfoBody body, @Path("userId") String userId);

    /**
     * 上传头像到七牛
     * @param userAvater
     * @return
     */
    @POST("uploads/uploadPic")
    Observable<Response<SimpleEntity>> uploadUserAvater(@Part("file") MultipartBody userAvater);

    /**
     * 保存头像地址
     * @param avatarUrl
     * @param userId
     * @return
     */
    @POST("v1/users/updateUserAvatarUrl/{userId}")
    Observable<Response<EmptyValueEntity>> modifyUserAvater(@Query("avatarUrl") String avatarUrl, @Path("userId") String userId);

}
