package com.wonders.xlab.pci.module.record.monitor.mvn.api;



import com.wonders.xlab.pci.mvn.entity.record.monitor.BPEntity;

import rx.Observable;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by sjy on 2015/12/22.
 */
public interface BPNAPI {
    @FormUrlEncoded
    @POST("userBloodPressure/listUserPressureByDate")
    Observable<BPEntity> getBp(@Field("userId") String userId, @Field("startDate") long startDate, @Field("endDate") long endDate);
}
