package com.wonders.xlab.pci.module.record.monitor.mvn.api;



import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.BPEntity;

import rx.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sjy on 2015/12/22.
 */
public interface BPAPI {
    @FormUrlEncoded
    @POST("userBloodPressure/listUserPressureByDate")
    Observable<BPEntity> getBp(@Field("userId") String userId, @Field("startDate") long startDate, @Field("endDate") long endDate);
}
