package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by WZH on 16/5/9.
 */
public interface ServiceAPI {
    @GET("healthServices/retrieveHealthServiceDetail/{serviceId}")
    Observable<Response<ServiceDetailEntity>> getServiceDetail(@Path("serviceId") Long serviceId);
}
