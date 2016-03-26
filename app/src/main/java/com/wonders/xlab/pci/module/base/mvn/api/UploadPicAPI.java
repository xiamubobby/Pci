package com.wonders.xlab.pci.module.base.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Observable<SimpleEntity> upload(@Part("userId") RequestBody userId, @Part("file") MultipartBody files);
}
