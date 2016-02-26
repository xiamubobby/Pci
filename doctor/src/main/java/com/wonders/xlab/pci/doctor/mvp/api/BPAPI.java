package com.wonders.xlab.pci.doctor.mvp.api;

import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface BPAPI {

    @GET("bloodPressures/listUserPressures/{userId}")
    Observable<BPEntity> getBPList(@Path("userId") String patientId, @Query("start") long startTime, @Query("end") long endTime);
}
