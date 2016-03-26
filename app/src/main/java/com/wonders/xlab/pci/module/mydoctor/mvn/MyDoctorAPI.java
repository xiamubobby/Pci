package com.wonders.xlab.pci.module.mydoctor.mvn;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hua on 16/1/28.
 */
public interface MyDoctorAPI {
    @FormUrlEncoded
    @POST("doctors/listDoctors")
    Observable<MyDoctorEntity> getDoctorList(@Field("userId") String userId, @Field("tel") String tel);
}
