package com.wonders.xlab.patient.mvp.api;


import java.util.IdentityHashMap;

import im.hua.library.base.mvp.entity.SimpleEntity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by hua on 15/12/17.
 */
public interface UploadPicAPI {
    @Multipart
    @POST("userCase/uploadPic")
    Observable<SimpleEntity> upload(@Part("userId") RequestBody userId, @PartMap IdentityHashMap<String, MultipartBody> files);

    @Multipart
    @POST("userCase/uploadPic")
    Observable<SimpleEntity> upload(@Part("userId") RequestBody userId, @Part("title") RequestBody title, @Part("file") MultipartBody multipartBody);
}