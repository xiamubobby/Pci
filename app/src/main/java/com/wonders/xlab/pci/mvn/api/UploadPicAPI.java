package com.wonders.xlab.pci.mvn.api;

import com.squareup.okhttp.RequestBody;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;

import java.util.IdentityHashMap;

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
