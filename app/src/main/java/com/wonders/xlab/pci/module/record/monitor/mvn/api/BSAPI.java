package com.wonders.xlab.pci.module.record.monitor.mvn.api;

import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.BSEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by sjy on 2015/12/23.
 */
public interface BSAPI {
    @FormUrlEncoded
    @POST("userBloodSugar/listUserBloodSugarByDate")
    Observable<BSEntity> getBS(@Field("userId") String userId, @Field("startDate") long startDate, @Field("endDate") long endDate);

}
