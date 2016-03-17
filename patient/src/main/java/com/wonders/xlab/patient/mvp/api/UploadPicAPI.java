package com.wonders.xlab.patient.mvp.api;

import com.squareup.okhttp.RequestBody;

import java.util.IdentityHashMap;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import rx.Observable;

/**
 * Created by hua on 15/12/17.
 */
public interface UploadPicAPI {
    @Multipart
    @POST("userCase/uploadPic")
    Observable<SimpleEntity> upload(@Part("userId") RequestBody userId, @PartMap IdentityHashMap<String, RequestBody> files);
}
