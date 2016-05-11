package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.RealNameValidateEntity;
import com.wonders.xlab.patient.mvp.entity.RegisterEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/5/4.
 */
public interface AuthAPI {
    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    @GET("v1/users/getCaptcha/{mobile}")
    Observable<Response<SimpleEntity>> getCaptcha(@Path("mobile") String mobile);

    /**
     * 注册
     * @param tel
     * @param password
     * @param captcha
     * @return
     */
    @FormUrlEncoded
    @POST("v1/users/register")
    Observable<Response<RegisterEntity>> register(@Field("tel") String tel, @Field("password") String password, @Field("captcha") String captcha);

    /**
     * 市民云认证
     * @param name
     * @param idCardNum
     * @param patientId
     * @param idCardPic
     * @return
     */
    @Multipart
    @POST("idCardValidates/validate")
    Observable<Response<SimpleEntity>> authorize(@Part("userId") RequestBody patientId,@Part("name") RequestBody name, @Part("idCardNum") RequestBody idCardNum, @Part("file") MultipartBody idCardPic);

    /**
     * 获取市民云实名认证审核结果
     * @param patientId
     * @return
     */
    @GET("idCardValidates/retrieveValidateResult/{userId}")
    Observable<Response<RealNameValidateEntity>> getRealNameValidateState(@Path("userId") String patientId);
}
