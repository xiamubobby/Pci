package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.OrderListEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jimmy on 16/5/9.
 */
public interface OrderListApi {

    /**
     * @param patientId 用户的id
     * @return
     */
    @GET("healthServiceOrders/listServiceOrders/{userId}")
    Observable<Response<OrderListEntity>> getOrderList(@Path("userId") String patientId, @Query("page") int page, @Query("size") int size);
}
