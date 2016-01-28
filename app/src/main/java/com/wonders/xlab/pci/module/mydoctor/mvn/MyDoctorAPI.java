package com.wonders.xlab.pci.module.mydoctor.mvn;

import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hua on 16/1/28.
 */
public interface MyDoctorAPI {
    @POST("doctor/List")
    Observable<MyDoctorEntity> getDoctorList(String userId,String tel);
}
