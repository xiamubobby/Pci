package com.wonders.xlab.pci.module.task.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hua on 16/1/6.
 */
public interface IdealRangeAPI {
    @GET("userBloodPressureStandard/getBloodPressureStandard/{userId}")
    Observable<SimpleEntity> fetchIdealBPRange(@Path("userId") String userId);

    @GET("userBloodSugarStandard/listBloodSugarStandard/{userId}")
    Observable<SimpleEntity> fetchIdealBSRange(@Path("userId") String userId);
}
