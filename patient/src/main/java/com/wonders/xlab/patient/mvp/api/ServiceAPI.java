package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import retrofit2.Response;
import retrofit2.http.GET;
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
}
