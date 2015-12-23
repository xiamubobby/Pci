package com.wonders.xlab.pci.module.record.monitor.mvn.api;



import com.wonders.xlab.pci.module.record.monitor.mvn.entity.BPEntity;
import rx.Observable;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by sjy on 2015/12/22.
 */
public interface BpAPI {
    @FormUrlEncoded
    @POST("userBloodPressure/listUserPressure")
    Observable<BPEntity> getBp(@Field("userId") String userId,@Field("startDate") Long startDate,@Field("endDate") Long endDate);
}
