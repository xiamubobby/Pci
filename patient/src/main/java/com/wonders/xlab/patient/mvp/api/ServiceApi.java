package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ServiceListEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by natsuki on 16/5/6.
 */
public interface ServiceAPI {
    /**
     * 获取所有健康服务
     *
     * @return
     */
    @GET("healthServices/listHealthService")
    Observable<Response<ServiceListEntity>> listHealthService();
}
