package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.GenerateOrderPaymentEntity;

import im.hua.library.base.mvp.entity.SimpleEntity;
import retrofit2.Response;
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
    Observable<Response<SimpleEntity>> orderPackage(@Field("userId") String patientId, @Field("packageId") String packageId,@Field("paymentChannel") String paymentChannel);

    @FormUrlEncoded
    @POST("v1/orders/generateOrderPayment")
    Observable<Response<GenerateOrderPaymentEntity>> generateOrderPayment(@Field("userId") String patientId, @Field("packageId") String packageId, @Field("paymentChannel") String paymentChannel);
}
