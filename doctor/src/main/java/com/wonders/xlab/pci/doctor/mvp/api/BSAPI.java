package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface BSAPI {

    @GET("bloodSugars/listBloodSugarRecord/{userId}")
    Observable<BSEntity> getBSList(@Path("userId") String userId);
}