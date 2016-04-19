package com.wonders.xlab.pci.doctor.data.api;

import com.wonders.xlab.pci.doctor.data.entity.BPEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/22.
 */
public interface BPAPI {

    @GET("v1/bloodPressures/listUserPressures/{userId}")
    Observable<Response<BPEntity>> getBPList(@Path("userId") String patientId, @Query("start") long startTime, @Query("end") long endTime,@Query("page") int page,@Query("size") int size);
}
