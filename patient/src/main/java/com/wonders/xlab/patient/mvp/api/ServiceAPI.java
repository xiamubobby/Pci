package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ServiceContentDetailEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceOrderEntity;

import im.hua.library.base.mvp.entity.BaseEntity;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by natsuki on 16/5/6.
 * Created by WZH on 16/5/9.
 */
public interface ServiceAPI {
    /**
     * 获取所有健康服务
     *
     * @return
     */
    @GET("healthServices/listHealthService")
    Observable<Response<ServiceListEntity>> listHealthService();

    @GET("healthServices/retrieveHealthServiceDetail/{serviceId}")
    Observable<Response<ServiceDetailEntity>> getServiceDetail(@Path("serviceId") Long serviceId);

    @GET("healthServices/retrieveHealthServiceContentDetail/{serviceId}")
    Observable<Response<ServiceContentDetailEntity>> getServiceContentDetail(@Path("serviceId") Long serviceId);

    @FormUrlEncoded
    @POST("healthServiceOrders/generateServiceOrder")
    Observable<Response<ServiceOrderEntity>> generateServiceOrder(@Field("userId") Long userId, @Field("specificationId") Long specificationId);
}
