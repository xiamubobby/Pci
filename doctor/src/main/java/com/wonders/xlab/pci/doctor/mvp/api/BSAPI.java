package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface BSAPI {

    @GET("v1/bloodSugars/listBloodSugarRecord/{userId}")
    Observable<Response<BSEntity>> getBSList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);
}