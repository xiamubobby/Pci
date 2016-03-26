package com.wonders.xlab.patient.mvp.api;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 16/3/21.
 */
public interface OrderPackageServiceAPI {
    @FormUrlEncoded
    @POST("v1/orders/bookDoctorGroup")
    Observable<SimpleEntity> orderPackage(@Field("userId") String patientId, @Field("packageId") String packageId);
}
