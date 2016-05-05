package com.wonders.xlab.patient.mvp.api;


import im.hua.library.base.mvp.entity.SimpleEntity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by hua on 15/12/17.
 */
public interface UploadPicAPI {

    @Multipart
    @POST("userCase/uploadPic")
    Observable<Response<SimpleEntity>> upload(@Part("userId") RequestBody userId, @Part("title") RequestBody title, @Part("file") MultipartBody multipartBody);
}