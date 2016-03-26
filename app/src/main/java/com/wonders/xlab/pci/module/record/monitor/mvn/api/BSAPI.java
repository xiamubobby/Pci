package com.wonders.xlab.pci.module.record.monitor.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.BSEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sjy on 2015/12/23.
 */
public interface BSAPI {
    @FormUrlEncoded
    @POST("userBloodSugar/listUserBloodSugarByDate")
    Observable<BSEntity> getBS(@Field("userId") String userId, @Field("startDate") long startDate, @Field("endDate") long endDate);

}
