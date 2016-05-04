package com.wonders.xlab.patient.mvp.api;


import com.wonders.xlab.patient.mvp.entity.MedicineRemindEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hua on 16/2/25.
 */
public interface MedicineRemindAPI {
    @GET("")
    Observable<Response<MedicineRemindEntity>> getMedicineRemindList(@Path("userId") String userId, @Query("page") int page, @Query("size") int size);
}
