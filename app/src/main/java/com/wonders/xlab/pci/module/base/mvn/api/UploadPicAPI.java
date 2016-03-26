package com.wonders.xlab.pci.module.base.mvn.api;

import com.squareup.okhttp.RequestBody;
import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;

import java.util.IdentityHashMap;

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
    Observable<SimpleEntity> upload(@Part("userId") RequestBody userId, @PartMap IdentityHashMap<String, RequestBody> files);
}
