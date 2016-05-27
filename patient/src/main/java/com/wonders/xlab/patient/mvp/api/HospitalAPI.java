package com.wonders.xlab.patient.mvp.api;

import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hua on 16/5/27.
 */
public interface HospitalAPI {
    /**
     * 获取医院字典表
     * @return
     */
    @GET("hospitals")
    Observable<Response<HospitalAllEntity>> getHospitals();
}
